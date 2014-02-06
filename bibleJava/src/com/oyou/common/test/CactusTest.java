package com.oyou.common.test;

import org.apache.cactus.ServletTestCase;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

public class CactusTest extends ServletTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    protected WebApplicationContext getWebApplicationContext() {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.config.getServletContext());
        return ctx;
    }

    protected Object getBean(String name) {
        return this.getWebApplicationContext().getBean(name);
    }

}
