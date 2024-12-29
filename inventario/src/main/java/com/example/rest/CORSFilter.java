package com.example.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        // Permitir solicitudes desde cualquier origen
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Permitir los métodos especificados
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        // Permitir los encabezados que serán enviados por el cliente
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        // Permitir credenciales (si es necesario)
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}
