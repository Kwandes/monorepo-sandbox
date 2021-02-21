package dev.hotdeals.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DataJpaTests
{
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Test
    void dataSourceIsNotNullTest()
    {
        assertThat(dataSource).isNotNull();
    }

    @Test
    void jdbcTemplateIsNotNullTest()
    {
        assertThat(jdbcTemplate).isNotNull();
    }

    @Test
    void entityManagerIsNotNullTest()
    {
        assertThat(entityManager).isNotNull();
    }
}
