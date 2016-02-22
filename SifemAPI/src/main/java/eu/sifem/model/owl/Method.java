package eu.sifem.model.owl;

import java.util.Collection;

import org.protege.owl.codegeneration.WrappedIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * <p>
 * Generated by Protege (http://protege.stanford.edu). <br>
 * Source Class: Method <br>
 * @version generated on Mon May 11 16:25:20 BST 2015 by jbjares
 */

public interface Method extends Activity {

    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/Simulation.owl#hasFirstWorkflowStep
     */
     
    /**
     * Gets all property values for the hasFirstWorkflowStep property.<p>
     * 
     * @returns a collection of values for the hasFirstWorkflowStep property.
     */
    Collection<? extends WrappedIndividual> getHasFirstWorkflowStep();

    /**
     * Checks if the class has a hasFirstWorkflowStep property value.<p>
     * 
     * @return true if there is a hasFirstWorkflowStep property value.
     */
    boolean hasHasFirstWorkflowStep();

    /**
     * Adds a hasFirstWorkflowStep property value.<p>
     * 
     * @param newHasFirstWorkflowStep the hasFirstWorkflowStep property value to be added
     */
    void addHasFirstWorkflowStep(WrappedIndividual newHasFirstWorkflowStep);

    /**
     * Removes a hasFirstWorkflowStep property value.<p>
     * 
     * @param oldHasFirstWorkflowStep the hasFirstWorkflowStep property value to be removed.
     */
    void removeHasFirstWorkflowStep(WrappedIndividual oldHasFirstWorkflowStep);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#generated
     */
     
    /**
     * Gets all property values for the generated property.<p>
     * 
     * @returns a collection of values for the generated property.
     */
    Collection<? extends Entity> getGenerated();

    /**
     * Checks if the class has a generated property value.<p>
     * 
     * @return true if there is a generated property value.
     */
    boolean hasGenerated();

    /**
     * Adds a generated property value.<p>
     * 
     * @param newGenerated the generated property value to be added
     */
    void addGenerated(Entity newGenerated);

    /**
     * Removes a generated property value.<p>
     * 
     * @param oldGenerated the generated property value to be removed.
     */
    void removeGenerated(Entity oldGenerated);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#invalidated
     */
     
    /**
     * Gets all property values for the invalidated property.<p>
     * 
     * @returns a collection of values for the invalidated property.
     */
    Collection<? extends Entity> getInvalidated();

    /**
     * Checks if the class has a invalidated property value.<p>
     * 
     * @return true if there is a invalidated property value.
     */
    boolean hasInvalidated();

    /**
     * Adds a invalidated property value.<p>
     * 
     * @param newInvalidated the invalidated property value to be added
     */
    void addInvalidated(Entity newInvalidated);

    /**
     * Removes a invalidated property value.<p>
     * 
     * @param oldInvalidated the invalidated property value to be removed.
     */
    void removeInvalidated(Entity oldInvalidated);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedAssociation
     */
     
    /**
     * Gets all property values for the qualifiedAssociation property.<p>
     * 
     * @returns a collection of values for the qualifiedAssociation property.
     */
    Collection<? extends Association> getQualifiedAssociation();

    /**
     * Checks if the class has a qualifiedAssociation property value.<p>
     * 
     * @return true if there is a qualifiedAssociation property value.
     */
    boolean hasQualifiedAssociation();

    /**
     * Adds a qualifiedAssociation property value.<p>
     * 
     * @param newQualifiedAssociation the qualifiedAssociation property value to be added
     */
    void addQualifiedAssociation(Association newQualifiedAssociation);

    /**
     * Removes a qualifiedAssociation property value.<p>
     * 
     * @param oldQualifiedAssociation the qualifiedAssociation property value to be removed.
     */
    void removeQualifiedAssociation(Association oldQualifiedAssociation);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedCommunication
     */
     
    /**
     * Gets all property values for the qualifiedCommunication property.<p>
     * 
     * @returns a collection of values for the qualifiedCommunication property.
     */
    Collection<? extends Communication> getQualifiedCommunication();

    /**
     * Checks if the class has a qualifiedCommunication property value.<p>
     * 
     * @return true if there is a qualifiedCommunication property value.
     */
    boolean hasQualifiedCommunication();

    /**
     * Adds a qualifiedCommunication property value.<p>
     * 
     * @param newQualifiedCommunication the qualifiedCommunication property value to be added
     */
    void addQualifiedCommunication(Communication newQualifiedCommunication);

    /**
     * Removes a qualifiedCommunication property value.<p>
     * 
     * @param oldQualifiedCommunication the qualifiedCommunication property value to be removed.
     */
    void removeQualifiedCommunication(Communication oldQualifiedCommunication);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedEnd
     */
     
    /**
     * Gets all property values for the qualifiedEnd property.<p>
     * 
     * @returns a collection of values for the qualifiedEnd property.
     */
    Collection<? extends End> getQualifiedEnd();

    /**
     * Checks if the class has a qualifiedEnd property value.<p>
     * 
     * @return true if there is a qualifiedEnd property value.
     */
    boolean hasQualifiedEnd();

    /**
     * Adds a qualifiedEnd property value.<p>
     * 
     * @param newQualifiedEnd the qualifiedEnd property value to be added
     */
    void addQualifiedEnd(End newQualifiedEnd);

    /**
     * Removes a qualifiedEnd property value.<p>
     * 
     * @param oldQualifiedEnd the qualifiedEnd property value to be removed.
     */
    void removeQualifiedEnd(End oldQualifiedEnd);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedStart
     */
     
    /**
     * Gets all property values for the qualifiedStart property.<p>
     * 
     * @returns a collection of values for the qualifiedStart property.
     */
    Collection<? extends Start> getQualifiedStart();

    /**
     * Checks if the class has a qualifiedStart property value.<p>
     * 
     * @return true if there is a qualifiedStart property value.
     */
    boolean hasQualifiedStart();

    /**
     * Adds a qualifiedStart property value.<p>
     * 
     * @param newQualifiedStart the qualifiedStart property value to be added
     */
    void addQualifiedStart(Start newQualifiedStart);

    /**
     * Removes a qualifiedStart property value.<p>
     * 
     * @param oldQualifiedStart the qualifiedStart property value to be removed.
     */
    void removeQualifiedStart(Start oldQualifiedStart);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedUsage
     */
     
    /**
     * Gets all property values for the qualifiedUsage property.<p>
     * 
     * @returns a collection of values for the qualifiedUsage property.
     */
    Collection<? extends Usage> getQualifiedUsage();

    /**
     * Checks if the class has a qualifiedUsage property value.<p>
     * 
     * @return true if there is a qualifiedUsage property value.
     */
    boolean hasQualifiedUsage();

    /**
     * Adds a qualifiedUsage property value.<p>
     * 
     * @param newQualifiedUsage the qualifiedUsage property value to be added
     */
    void addQualifiedUsage(Usage newQualifiedUsage);

    /**
     * Removes a qualifiedUsage property value.<p>
     * 
     * @param oldQualifiedUsage the qualifiedUsage property value to be removed.
     */
    void removeQualifiedUsage(Usage oldQualifiedUsage);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#used
     */
     
    /**
     * Gets all property values for the used property.<p>
     * 
     * @returns a collection of values for the used property.
     */
    Collection<? extends Entity> getUsed();

    /**
     * Checks if the class has a used property value.<p>
     * 
     * @return true if there is a used property value.
     */
    boolean hasUsed();

    /**
     * Adds a used property value.<p>
     * 
     * @param newUsed the used property value to be added
     */
    void addUsed(Entity newUsed);

    /**
     * Removes a used property value.<p>
     * 
     * @param oldUsed the used property value to be removed.
     */
    void removeUsed(Entity oldUsed);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasAssociatedWith
     */
     
    /**
     * Gets all property values for the wasAssociatedWith property.<p>
     * 
     * @returns a collection of values for the wasAssociatedWith property.
     */
    Collection<? extends Agent> getWasAssociatedWith();

    /**
     * Checks if the class has a wasAssociatedWith property value.<p>
     * 
     * @return true if there is a wasAssociatedWith property value.
     */
    boolean hasWasAssociatedWith();

    /**
     * Adds a wasAssociatedWith property value.<p>
     * 
     * @param newWasAssociatedWith the wasAssociatedWith property value to be added
     */
    void addWasAssociatedWith(Agent newWasAssociatedWith);

    /**
     * Removes a wasAssociatedWith property value.<p>
     * 
     * @param oldWasAssociatedWith the wasAssociatedWith property value to be removed.
     */
    void removeWasAssociatedWith(Agent oldWasAssociatedWith);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasEndedBy
     */
     
    /**
     * Gets all property values for the wasEndedBy property.<p>
     * 
     * @returns a collection of values for the wasEndedBy property.
     */
    Collection<? extends Entity> getWasEndedBy();

    /**
     * Checks if the class has a wasEndedBy property value.<p>
     * 
     * @return true if there is a wasEndedBy property value.
     */
    boolean hasWasEndedBy();

    /**
     * Adds a wasEndedBy property value.<p>
     * 
     * @param newWasEndedBy the wasEndedBy property value to be added
     */
    void addWasEndedBy(Entity newWasEndedBy);

    /**
     * Removes a wasEndedBy property value.<p>
     * 
     * @param oldWasEndedBy the wasEndedBy property value to be removed.
     */
    void removeWasEndedBy(Entity oldWasEndedBy);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasInformedBy
     */
     
    /**
     * Gets all property values for the wasInformedBy property.<p>
     * 
     * @returns a collection of values for the wasInformedBy property.
     */
    Collection<? extends Activity> getWasInformedBy();

    /**
     * Checks if the class has a wasInformedBy property value.<p>
     * 
     * @return true if there is a wasInformedBy property value.
     */
    boolean hasWasInformedBy();

    /**
     * Adds a wasInformedBy property value.<p>
     * 
     * @param newWasInformedBy the wasInformedBy property value to be added
     */
    void addWasInformedBy(Activity newWasInformedBy);

    /**
     * Removes a wasInformedBy property value.<p>
     * 
     * @param oldWasInformedBy the wasInformedBy property value to be removed.
     */
    void removeWasInformedBy(Activity oldWasInformedBy);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasStartedBy
     */
     
    /**
     * Gets all property values for the wasStartedBy property.<p>
     * 
     * @returns a collection of values for the wasStartedBy property.
     */
    Collection<? extends Entity> getWasStartedBy();

    /**
     * Checks if the class has a wasStartedBy property value.<p>
     * 
     * @return true if there is a wasStartedBy property value.
     */
    boolean hasWasStartedBy();

    /**
     * Adds a wasStartedBy property value.<p>
     * 
     * @param newWasStartedBy the wasStartedBy property value to be added
     */
    void addWasStartedBy(Entity newWasStartedBy);

    /**
     * Removes a wasStartedBy property value.<p>
     * 
     * @param oldWasStartedBy the wasStartedBy property value to be removed.
     */
    void removeWasStartedBy(Entity oldWasStartedBy);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#endedAtTime
     */
     
    /**
     * Gets all property values for the endedAtTime property.<p>
     * 
     * @returns a collection of values for the endedAtTime property.
     */
    Collection<? extends Object> getEndedAtTime();

    /**
     * Checks if the class has a endedAtTime property value.<p>
     * 
     * @return true if there is a endedAtTime property value.
     */
    boolean hasEndedAtTime();

    /**
     * Adds a endedAtTime property value.<p>
     * 
     * @param newEndedAtTime the endedAtTime property value to be added
     */
    void addEndedAtTime(Object newEndedAtTime);

    /**
     * Removes a endedAtTime property value.<p>
     * 
     * @param oldEndedAtTime the endedAtTime property value to be removed.
     */
    void removeEndedAtTime(Object oldEndedAtTime);



    /* ***************************************************
     * Property http://www.w3.org/ns/prov#startedAtTime
     */
     
    /**
     * Gets all property values for the startedAtTime property.<p>
     * 
     * @returns a collection of values for the startedAtTime property.
     */
    Collection<? extends Object> getStartedAtTime();

    /**
     * Checks if the class has a startedAtTime property value.<p>
     * 
     * @return true if there is a startedAtTime property value.
     */
    boolean hasStartedAtTime();

    /**
     * Adds a startedAtTime property value.<p>
     * 
     * @param newStartedAtTime the startedAtTime property value to be added
     */
    void addStartedAtTime(Object newStartedAtTime);

    /**
     * Removes a startedAtTime property value.<p>
     * 
     * @param oldStartedAtTime the startedAtTime property value to be removed.
     */
    void removeStartedAtTime(Object oldStartedAtTime);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
