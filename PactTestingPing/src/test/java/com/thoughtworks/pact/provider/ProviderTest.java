package com.thoughtworks.pact.provider;

/**
 * Created by pingzhu on 5/14/16.
 */

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.loader.PactSource;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.apache.http.HttpRequest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PactRunner.class) // Say JUnit to run tests with custom Runner
@Provider("foo_provider") // Set up name of tested provider
@PactFolder("src/test/resources/pacts") // Point where to find pacts (See also section Pacts source in documentation)
public class ProviderTest {
    // NOTE: this is just an example of embedded service that listens to requests, you should start here real service
    //@ClassRule //Rule will be applied once: before/after whole contract test suite


    @BeforeClass //Method will be run once: before whole contract test suite
    public static void setUpService() {
        //Run DB, create schema
        //Run service
        //...
    }

    @Before //Method will be run before each test of interaction
    public void before() {
        // Rest data
        // Mock dependent service responses
        // ...

    }

    @State("default") // Method will be run before testing interactions that require "default" or "no-data" state
    public void toDefaultState() {
        // Prepare service before interaction that require "default" state
        // ...
        System.out.println("Now service in default state");
    };

    @TestTarget // Annotation denotes Target that will be used for tests
    public final Target target = new HttpTarget(8332);
    // Out-of-the-box implementation of Target (for more information take a look at Test Target section)
}
