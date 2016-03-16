package com.ems.espupdate.service;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Created by sbn on 01.09.2014.
 */
public class FlywayIntegrator implements Integrator {

    private static final Logger logger = LoggerFactory.getLogger(FlywayIntegrator.class);

    @Override
    public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
        logger.info("Starting Flyway Migration");
        final Flyway flyway = new Flyway();
        try {
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:/jdbc/espupdate");
            flyway.setDataSource(dataSource);
            flyway.setBaselineOnMigrate(true);
            for (MigrationInfo i : flyway.info().all()) {
                logger.info("migrate task: " + i.getVersion() + " : "
                        + i.getDescription() + " from file: " + i.getScript());
            }
            flyway.repair();
            flyway.migrate();
        } catch (Exception e) {
            logger.error("Error while migrating:", e);
        }
        logger.info("Finished Flyway Migration");
    }

    @Override
    public void integrate(MetadataImplementor metadataImplementor, SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {

    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {

    }
}
