// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.research.ws.wadl;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    public Doc createDoc() {
        return new Doc();
    }
    
    public ResourceType createResourceType() {
        return new ResourceType();
    }
    
    public Resources createResources() {
        return new Resources();
    }
    
    public Request createRequest() {
        return new Request();
    }
    
    public Link createLink() {
        return new Link();
    }
    
    public Option createOption() {
        return new Option();
    }
    
    public Resource createResource() {
        return new Resource();
    }
    
    public Param createParam() {
        return new Param();
    }
    
    public Method createMethod() {
        return new Method();
    }
    
    public Response createResponse() {
        return new Response();
    }
    
    public Include createInclude() {
        return new Include();
    }
    
    public Application createApplication() {
        return new Application();
    }
    
    public Grammars createGrammars() {
        return new Grammars();
    }
    
    public Representation createRepresentation() {
        return new Representation();
    }
}
