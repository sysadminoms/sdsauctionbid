package com.oms.sdsauctionbid.utils;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class StringSequenceIdentifier implements IdentifierGenerator, Configurable {
    private static final Logger LOG = LoggerFactory.getLogger("StringSequenceIdentifier");
    public static final String SEQUENCE_PREFIX = "sequence_prefix";
    public static final String COLUMN_NAME = "column_name";
    public static final String TABLE_NAME = "table_name";
    private String sequencePrefix;
    private String columnName;
    private String tableName;

    @Override
    public void configure(
            Type type, Properties params, ServiceRegistry serviceRegistry)
            throws MappingException {

        sequencePrefix = ConfigurationHelper.getString(
                SEQUENCE_PREFIX,
                params);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor si, Object o) {

        String suffix = "0001";
        String prefix = "A";
        PreparedStatement pst = null;
        Connection con = si.connection();
        try {
            pst = con.prepareStatement("SELECT id FROM " + "sds_user_details ORDER BY ID DESC LIMIT 1");
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                if (rs != null) {
                    suffix = rs.getString("id").substring(1);
                    prefix = rs.getString("id").substring(0, 1);
                    if ("9999".equals(suffix)) {
                        prefix = Character.toString((char) (prefix.toCharArray()[0] + 1));
                        suffix = "0001";
                    } else {
                        suffix = String.format("%04d", Integer.parseInt(suffix) + 1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prefix.concat(suffix);
    }

}
