package org.springframework.samples.petclinic.domain.owner.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.domain.owner.PetRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for Pet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetQueryWiring implements RuntimeWiringConfigurer {
    private final PetRepository petRepository;

    public PetQueryWiring(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", wiring -> wiring
            .dataFetcher("pet", env -> {
                int id = env.getArgument("id");
                return petRepository.findById(id);
            })
            .dataFetcher("pets", env -> List.copyOf(petRepository.findAll()))
        );
    }
}
