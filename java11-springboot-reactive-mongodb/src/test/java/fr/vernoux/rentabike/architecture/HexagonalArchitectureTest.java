package fr.vernoux.rentabike.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class HexagonalArchitectureTest {

    private final String[] domainPackages = new String[]{
            "fr.vernoux.rentabike.domain..",
            "java..",
            "reactor.core..",
            "org.apache.commons.lang3.."
    };
    private final JavaClasses projectClasses = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("fr.vernoux.rentabike");

    @Test
    void domainLayerDependsOnNothing() {
        classes()
                .that().resideInAPackage("fr.vernoux.rentabike.domain..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(domainPackages)
                .check(projectClasses);
    }

    @Test
    void applicationLayerDependsOnDomainLayerOnly() {
        String[] applicationPackages = ArrayUtils.addAll(domainPackages, "fr.vernoux.rentabike.application..");
        classes()
                .that().resideInAPackage("fr.vernoux.rentabike.application..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(applicationPackages)
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
