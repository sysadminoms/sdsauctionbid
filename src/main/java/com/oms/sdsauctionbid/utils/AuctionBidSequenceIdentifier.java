package com.oms.sdsauctionbid.utils;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.Instant;
import java.util.Properties;

public class AuctionBidSequenceIdentifier implements IdentifierGenerator, Configurable {
    private static final Logger LOG = LoggerFactory.getLogger("AuctionBidSequenceIdentifier");

    @Override
    public void configure(
            Type type, Properties params, ServiceRegistry serviceRegistry)
            throws MappingException {
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor si, Object o) {
        return Long.toString(Instant.now().toEpochMilli());
    }

}
