package com.example.springstudy;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;

/**
 * Created by Acer on 22.05.2017.
 */
public class VersionFilter extends ZuulFilter {
    private EurekaClient eurekaClient;

    public VersionFilter(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        try {
            String appropriateRoutingUrl = getAppropriateRoutingUrl();
            //String formQuery = formQuery(requestContext);
            requestContext.setRouteHost(new URL(appropriateRoutingUrl/* + formQuery*/));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAppropriateRoutingUrl() {
        List<Application> instances = eurekaClient.getApplications().getRegisteredApplications();

        List<InstanceInfo> servers = null;
        for (Application app : instances) {
            if ("server".equals(app.getName().toLowerCase())) {
                servers = app.getInstances();
                break;
            }
        }

        if (servers == null || servers.isEmpty()) {
            return EMPTY_STRING;
        }

        InstanceInfo maxVersionServer = servers.get(0);
        int version = Integer.valueOf(maxVersionServer.getMetadata().get("version"));
        for (InstanceInfo server : servers) {
            int newVersion = Integer.valueOf(server.getMetadata().get("version"));
            if (newVersion > version) {
                maxVersionServer = server;
                version = Integer.valueOf(maxVersionServer.getMetadata().get("version"));
            }
        }
        return maxVersionServer.getHomePageUrl();
    }

    private String formQuery(RequestContext context) {
        if (context == null || context.getRequest() == null) {
            return EMPTY_STRING;
        }
        return context.getRequest().getRequestURI().substring(8) + "?" + context.getRequest().getQueryString();
    }
}
