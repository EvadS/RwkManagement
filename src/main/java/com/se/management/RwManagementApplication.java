package com.se.management;

import com.se.management.domain.Searcher;
import com.se.management.domain.SkillsScore;
import com.se.management.model.request.SkillScoreRequest;
import com.se.management.repository.SearcherRepository;
import com.se.management.repository.SkillsScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class RwManagementApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RwManagementApplication.class, args);
    }

    @Autowired
    private SearcherRepository searcherRepository;

    @Autowired
    private SkillsScoreRepository skillsScoreRepository;

    @Override
    public void run(String... args) throws Exception {
            testSearcher();

     }

    private void testSearcher() {

        skillsScoreRepository.deleteAllInBatch();
        searcherRepository.deleteAllInBatch();

        Searcher searcher = Searcher.builder()
                .email("mail@mail.com")
                .firstName("first name")
                .lastName("last Name")
                .build();

        searcherRepository.save(searcher);

        SkillsScore skill = SkillsScore.builder()
                .skill("Java")
                .score((byte)1)
                .searcher(searcher)
                .build();

        skillsScoreRepository.save(skill);

        SkillsScore skill2 = SkillsScore.builder()
                .skill("C#")
                .score((byte)1)
                .searcher(searcher)
                .build();

        skillsScoreRepository.save(skill2);

        Searcher searcher2 = Searcher.builder()
                .email("mail2@mail.com")
                .firstName("fn2")
                .lastName("ln2")
                .build();

        SkillsScore skillScore3 = SkillsScore.builder()
                .score((byte)10)
                .skill("Js s2")
               .searcher(searcher2)
                .build();

        skillsScoreRepository.save(skillScore3);

        SkillsScore skillScore4 = SkillsScore.builder()
                .score((byte)10)
                .skill("TS s2")
                .searcher(searcher2)
                .build();
        skillsScoreRepository.save(skillScore3);


     //   searcher2.getSkillsScores().add(skillScore3);
      //  searcher2.getSkillsScores().add(skillScore4);

        // No cascade
      //  searcher2.getSkillsScores().add(skillScore3);

         searcherRepository.save(searcher2);

         List<Searcher> searcherList = searcherRepository.findAll();

        System.out.println("--- res --->");
        searcherList.forEach(System.out::println);
        System.out.println("<<--- res ---");

//        searcher2.getSkillsScores().stream().forEach(it-> {
                skillsScoreRepository.deleteAll(searcher2.getSkillsScores());
//        });

//        skillScore3.setSearcher(searcher2);
//        skillScore4.setSearcher(searcher2);

        skillsScoreRepository.save(skillScore3);
        skillsScoreRepository.save(skillScore4);

        int a =9;

    }
}

