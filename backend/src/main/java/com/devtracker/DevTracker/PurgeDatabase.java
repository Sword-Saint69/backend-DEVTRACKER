package com.devtracker.DevTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PurgeDatabase {
    
    private static final Logger logger = LoggerFactory.getLogger(PurgeDatabase.class);
    
    public static void main(String[] args) {
        logger.info("🚀 Starting Devtracker database purge process...");
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("⚠️  WARNING: This will DELETE ALL DATA from the Devtracker database!");
        System.out.println("   This operation cannot be undone.");
        System.out.print("Type 'PURGE' to confirm database purge: ");
        
        String confirmation = scanner.nextLine();
        
        if (!"PURGE".equals(confirmation)) {
            System.out.println("❌ Purge cancelled. You must type 'PURGE' to confirm.");
            return;
        }
        
        try {
            // Get database configuration from environment variables or use defaults
            String dbUrl = System.getenv("DB_URL");
            if (dbUrl == null || dbUrl.isEmpty()) {
                dbUrl = "jdbc:postgresql://ep-delicate-hat-addc72w0-pooler.c-2.us-east-1.aws.neon.tech/devtracker?sslmode=require&channel_binding=require";
            }
            
            String dbUsername = System.getenv("DB_USERNAME");
            if (dbUsername == null || dbUsername.isEmpty()) {
                dbUsername = "neondb_owner";
            }
            
            String dbPassword = System.getenv("DB_PASSWORD");
            if (dbPassword == null || dbPassword.isEmpty()) {
                dbPassword = "npg_9sbLrpR4JnfC";
            }
            
            System.out.println("🔍 Connecting to database...");
            System.out.println("   URL: " + dbUrl);
            System.out.println("   Username: " + dbUsername);
            
            // Create data source
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(dbUrl);
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);
            dataSource.setDriverClassName("org.postgresql.Driver");
            
            // Test connection
            try (Connection conn = dataSource.getConnection()) {
                DatabaseMetaData metaData = conn.getMetaData();
                String dbName = conn.getCatalog();
                System.out.println("✅ Connected to database: " + dbName);
                
                // Confirm this is the correct database
                if (!"devtracker".equalsIgnoreCase(dbName) && !"neondb".equalsIgnoreCase(dbName)) {
                    System.out.println("⚠️  Warning: Connected to database '" + dbName + "', not 'devtracker' or 'neondb'");
                    System.out.print("Continue with purge? Type 'YES' to confirm: ");
                    String dbConfirmation = scanner.nextLine();
                    if (!"YES".equals(dbConfirmation)) {
                        System.out.println("❌ Purge cancelled.");
                        return;
                    }
                }
            }
            
            // Create JdbcTemplate
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            
            // Get list of all tables
            List<String> tables = jdbcTemplate.query(
                "SELECT tablename FROM pg_tables WHERE schemaname = 'public'",
                (rs, rowNum) -> rs.getString("tablename")
            );
            
            System.out.println("📝 Found " + tables.size() + " tables to purge");
            
            if (tables.isEmpty()) {
                System.out.println("✅ No tables found. Database is already empty.");
                return;
            }
            
            // Disable foreign key checks
            System.out.println("🔓 Disabling foreign key constraints...");
            jdbcTemplate.execute("SET session_replication_role = 'replica'");
            
            // Truncate all tables
            System.out.println("🗑️  Purging all tables...");
            for (String table : tables) {
                System.out.println("   • Purging table: " + table);
                jdbcTemplate.execute("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE");
            }
            
            // Re-enable foreign key checks
            System.out.println("🔒 Re-enabling foreign key constraints...");
            jdbcTemplate.execute("SET session_replication_role = 'origin'");
            
            System.out.println("✅ Database purge completed successfully!");
            System.out.println("   All data has been removed from the database.");
            
        } catch (Exception e) {
            logger.error("❌ Failed to purge database", e);
            System.out.println("❌ Failed to purge database: " + e.getMessage());
            System.out.println("💡 Make sure the database is running and credentials are correct.");
            System.exit(1);
        }
    }
}