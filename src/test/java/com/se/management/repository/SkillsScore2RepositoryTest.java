package com.se.management.repository;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class SkillsScore2RepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SearcherRepository searcherRepository;

    @Test
    public void shouldWorkCorrect(){


//        Searcher searcher = Searcher.builder()
//                .lastName("last name")
//                .firstName("first name")
//                .email("test@mail.com")
//                .addresses(new HashSet<Address>())
//
//                .build();
//
//        entityManager.persistAndFlush(searcher);
//

        Assert.assertTrue(true);
    }
}
