package com.zenika.rentabike.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class HexagonalArchitectureTest {

    private final String[] domainPackages = new String[]{
            "com.zenika.rentabike.domain..",
            "java..",
            "org.apache.commons.lang3.."
    };
    private final JavaClasses projectClasses = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("com.zenika.rentabike");

    @Test
    void domainLayerDependsOnNothing() {
        classes()
                .that().resideInAPackage("com.zenika.rentabike.domain..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(domainPackages)
                .check(projectClasses);
    }

    @Test
    void applicationLayerDependsOnDomainLayerOnly() {
        classes()
                .that().resideInAPackage("com.zenika.rentabike.application..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(domainPackages)
                .check(projectClasses);
    }

    @Test
    void infrastructureLayerCannotDependOnExpositionLayer() {
        noClasses()
                .that().resideInAPackage("com.zenika.rentabike.infrastructure..")
                .should().dependOnClassesThat().resideInAnyPackage("com.zenika.rentabike.exposition..")
                .check(projectClasses);
    }

    @Test
    void expositionLayerCanDependsOnInfrastructureLayer() {
        noClasses()
                .that().resideInAPackage("com.zenika.rentabike.exposition..")
                .should().dependOnClassesThat().resideInAnyPackage("com.zenika.rentabike.infrastructure..")
                .check(projectClasses);
    }
}
