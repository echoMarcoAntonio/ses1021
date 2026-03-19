package br.com.ses.config.junit;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

public class JUnitTestRunner {

    public static void main(String[] args) throws URISyntaxException {
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener summary = new SummaryGeneratingListener();

        Path testClasses = Paths.get(
                JUnitTestRunner.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()
        );

        LauncherDiscoveryRequest request = request()
                .selectors(DiscoverySelectors.selectClasspathRoots(Set.of(testClasses)))
                .build();

        // imprime cada teste conforme executa
        TestExecutionListener printer = new TestExecutionListener() {

            @Override
            public void executionStarted(TestIdentifier id) {
                if (id.isTest()) {
                    System.out.println("\nTeste processando... " + id.getDisplayName());
                }
            }

            @Override
            public void executionFinished(TestIdentifier id, TestExecutionResult result) {
                if (!id.isTest()) return;

                switch (result.getStatus()) {
                    case SUCCESSFUL -> System.out.println(id.getDisplayName() + " foi executado com sucesso!!!");
                    case FAILED     -> {
                        System.out.println("O teste falhou!!! " + id.getDisplayName());
                        result.getThrowable().ifPresent(Throwable::printStackTrace);
                    }
                    case ABORTED    -> System.out.println("Operação abortada!!! " + id.getDisplayName());
                }
            }

            @Override
            public void executionSkipped(TestIdentifier id, String reason) {
                if (id.isTest()) {
                    System.out.println("O teste foi pulado!!!  " + id.getDisplayName() + " (" + reason + ")");
                }
            }
        };

        launcher.execute(request, summary, printer);

        System.out.println("\n--------------- Sumário ---------------");
        try (PrintWriter writer = new PrintWriter(System.out)) {
            summary.getSummary().printTo(writer);
        }
    }
}
