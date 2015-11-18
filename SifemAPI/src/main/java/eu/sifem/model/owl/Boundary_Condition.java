package eu.sifem.model.owl;

import java.util.Collection;

import org.protege.owl.codegeneration.WrappedIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * <p>
 * Generated by Protege (http://protege.stanford.edu). <br>
 * Source Class: Boundary_Condition <br>
 * @version generated on Mon May 11 16:25:19 BST 2015 by jbjares
 */

public interface Boundary_Condition extends WrappedIndividual {

    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#isBoundaryConditionOf
     */
     
    /**
     * Gets all property values for the isBoundaryConditionOf property.<p>
     * 
     * @returns a collection of values for the isBoundaryConditionOf property.
     */
    Collection<? extends Physical_Attribute> getIsBoundaryConditionOf();

    /**
     * Checks if the class has a isBoundaryConditionOf property value.<p>
     * 
     * @return true if there is a isBoundaryConditionOf property value.
     */
    boolean hasIsBoundaryConditionOf();

    /**
     * Adds a isBoundaryConditionOf property value.<p>
     * 
     * @param newIsBoundaryConditionOf the isBoundaryConditionOf property value to be added
     */
    void addIsBoundaryConditionOf(Physical_Attribute newIsBoundaryConditionOf);

    /**
     * Removes a isBoundaryConditionOf property value.<p>
     * 
     * @param oldIsBoundaryConditionOf the isBoundaryConditionOf property value to be removed.
     */
    void removeIsBoundaryConditionOf(Physical_Attribute oldIsBoundaryConditionOf);


    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasMultiplier
     */
     
    /**
     * Gets all property values for the hasMultiplier property.<p>
     * 
     * @returns a collection of values for the hasMultiplier property.
     */
    Collection<? extends Object> getHasMultiplier();

    /**
     * Checks if the class has a hasMultiplier property value.<p>
     * 
     * @return true if there is a hasMultiplier property value.
     */
    boolean hasHasMultiplier();

    /**
     * Adds a hasMultiplier property value.<p>
     * 
     * @param newHasMultiplier the hasMultiplier property value to be added
     */
    void addHasMultiplier(Object newHasMultiplier);

    /**
     * Removes a hasMultiplier property value.<p>
     * 
     * @param oldHasMultiplier the hasMultiplier property value to be removed.
     */
    void removeHasMultiplier(Object oldHasMultiplier);



    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasPatchCondition
     */
     
    /**
     * Gets all property values for the hasPatchCondition property.<p>
     * 
     * @returns a collection of values for the hasPatchCondition property.
     */
    Collection<? extends Object> getHasPatchCondition();

    /**
     * Checks if the class has a hasPatchCondition property value.<p>
     * 
     * @return true if there is a hasPatchCondition property value.
     */
    boolean hasHasPatchCondition();

    /**
     * Adds a hasPatchCondition property value.<p>
     * 
     * @param newHasPatchCondition the hasPatchCondition property value to be added
     */
    void addHasPatchCondition(Object newHasPatchCondition);

    /**
     * Removes a hasPatchCondition property value.<p>
     * 
     * @param oldHasPatchCondition the hasPatchCondition property value to be removed.
     */
    void removeHasPatchCondition(Object oldHasPatchCondition);



    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasTimeFunction
     */
     
    /**
     * Gets all property values for the hasTimeFunction property.<p>
     * 
     * @returns a collection of values for the hasTimeFunction property.
     */
    Collection<? extends Object> getHasTimeFunction();

    /**
     * Checks if the class has a hasTimeFunction property value.<p>
     * 
     * @return true if there is a hasTimeFunction property value.
     */
    boolean hasHasTimeFunction();

    /**
     * Adds a hasTimeFunction property value.<p>
     * 
     * @param newHasTimeFunction the hasTimeFunction property value to be added
     */
    void addHasTimeFunction(Object newHasTimeFunction);

    /**
     * Removes a hasTimeFunction property value.<p>
     * 
     * @param oldHasTimeFunction the hasTimeFunction property value to be removed.
     */
    void removeHasTimeFunction(Object oldHasTimeFunction);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}