package org.itallix.restaurant.services;

import org.itallix.restaurant.TestFixtures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class AutoDevDataPopulator implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger log = LoggerFactory.getLogger(AutoDevDataPopulator.class);

    @Autowired
    private TestFixtures testFixtures;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Recreating DB");
        testFixtures.initTestDb();
        testFixtures.createAccounts();
        testFixtures.createRestaurant();
        log.debug("Test data added");
    }
}
