package local.springframework.repositories;

import local.springframework.model.UnitOfMeasure;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository repository;

    @Before
    public void setUp() {
    }

    @Test
    public void findByDescription() {
        log.info("testing findByDescription");
        Optional<UnitOfMeasure> unitOfMeasureOptional = repository.findByDescription("teaspoon");
        assertEquals("teaspoon", unitOfMeasureOptional.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        log.info("testing findByDescriptionCup");
        Optional<UnitOfMeasure> unitOfMeasureOptional = repository.findByDescription("cup");
        assertEquals("cup", unitOfMeasureOptional.get().getDescription());
    }
}