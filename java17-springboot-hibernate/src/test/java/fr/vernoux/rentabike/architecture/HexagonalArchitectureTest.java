package fr.vernoux.rentabike.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class HexagonalArchitectureTest {

    private final JavaClasses projectClasses = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("fr.vernoux.rentabike");

    @Test
    void domainLayerDependsOnNothing() {
        classes()
                .that().resideInAPackage("fr.vernoux.rentabike.domain..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("fr.vernoux.rentabike.domain..", "java..")
                .check(projectClasses);
    }

    @Test
    void applicationLayerDependsOnDomainLayerOnlyAndHandlesTransactions() {
        classes()
                .that().resideInAPackage("fr.vernoux.rentabike.application..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("fr.vernoux.rentabike.domain..", "java..", "jakarta.transaction..")
                .check(projectClasses);
    }

    @Test
    void infrastructureLayerCannotDependOnExpositionLayer() {
        noClasses()
                .that().resideInAPackage("fr.vernoux.rentabike.infrastructure..")
                .should().dependOnClassesThat().resideInAnyPackage("fr.vernoux.rentabike.exposition..")
                .check(projectClasses);
    }

    @Test
    void expositionLayerCanDependsOnInfrastructureLayer() {
        noClasses()
                .that().resideInAPackage("fr.vernoux.rentabike.exposition..")
                .should().dependOnClassesThat().resideInAnyPackage("fr.vernoux.rentabike.infrastructure..")
                .check(projectClasses);
    }
}
