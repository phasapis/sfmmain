package eu.sifem.model.owl;

import java.util.Collection;

import org.protege.owl.codegeneration.WrappedIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * <p>
 * Generated by Protege (http://protege.stanford.edu). <br>
 * Source Class: Velocity <br>
 * @version generated on Mon May 11 16:25:20 BST 2015 by jbjares
 */

public interface Velocity extends Physical_Attribute {

    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasBoundaryCondition
     */
     
    /**
     * Gets all property values for the hasBoundaryCondition property.<p>
     * 
     * @returns a collection of values for the hasBoundaryCondition property.
     */
    Collection<? extends Boundary_Condition> getHasBoundaryCondition();

    /**
     * Checks if the class has a hasBoundaryCondition property value.<p>
     * 
     * @return true if there is a hasBoundaryCondition property value.
     */
    boolean hasHasBoundaryCondition();

    /**
     * Adds a hasBoundaryCondition property value.<p>
     * 
     * @param newHasBoundaryCondition the hasBoundaryCondition property value to be added
     */
    void addHasBoundaryCondition(Boundary_Condition newHasBoundaryCondition);

    /**
     * Removes a hasBoundaryCondition property value.<p>
     * 
     * @param oldHasBoundaryCondition the hasBoundaryCondition property value to be removed.
     */
    void removeHasBoundaryCondition(Boundary_Condition oldHasBoundaryCondition);


    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasValueHeldBy
     */
     
    /**
     * Gets all property values for the hasValueHeldBy property.<p>
     * 
     * @returns a collection of values for the hasValueHeldBy property.
     */
    Collection<? extends WrappedIndividual> getHasValueHeldBy();

    /**
     * Checks if the class has a hasValueHeldBy property value.<p>
     * 
     * @return true if there is a hasValueHeldBy property value.
     */
    boolean hasHasValueHeldBy();

    /**
     * Adds a hasValueHeldBy property value.<p>
     * 
     * @param newHasValueHeldBy the hasValueHeldBy property value to be added
     */
    void addHasValueHeldBy(WrappedIndividual newHasValueHeldBy);

    /**
     * Removes a hasValueHeldBy property value.<p>
     * 
     * @param oldHasValueHeldBy the hasValueHeldBy property value to be removed.
     */
    void removeHasValueHeldBy(WrappedIndividual oldHasValueHeldBy);


    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#isInputOfApplied
     */
     
    /**
     * Gets all property values for the isInputOfApplied property.<p>
     * 
     * @returns a collection of values for the isInputOfApplied property.
     */
    Collection<? extends Solver> getIsInputOfApplied();

    /**
     * Checks if the class has a isInputOfApplied property value.<p>
     * 
     * @return true if there is a isInputOfApplied property value.
     */
    boolean hasIsInputOfApplied();

    /**
     * Adds a isInputOfApplied property value.<p>
     * 
     * @param newIsInputOfApplied the isInputOfApplied property value to be added
     */
    void addIsInputOfApplied(Solver newIsInputOfApplied);

    /**
     * Removes a isInputOfApplied property value.<p>
     * 
     * @param oldIsInputOfApplied the isInputOfApplied property value to be removed.
     */
    void removeIsInputOfApplied(Solver oldIsInputOfApplied);


    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#isConstrained
     */
     
    /**
     * Gets all property values for the isConstrained property.<p>
     * 
     * @returns a collection of values for the isConstrained property.
     */
    Collection<? extends Boolean> getIsConstrained();

    /**
     * Checks if the class has a isConstrained property value.<p>
     * 
     * @return true if there is a isConstrained property value.
     */
    boolean hasIsConstrained();

    /**
     * Adds a isConstrained property value.<p>
     * 
     * @param newIsConstrained the isConstrained property value to be added
     */
    void addIsConstrained(Boolean newIsConstrained);

    /**
     * Removes a isConstrained property value.<p>
     * 
     * @param oldIsConstrained the isConstrained property value to be removed.
     */
    void removeIsConstrained(Boolean oldIsConstrained);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}