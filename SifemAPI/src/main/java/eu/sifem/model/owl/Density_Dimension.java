package eu.sifem.model.owl;

import java.util.Collection;

import org.protege.owl.codegeneration.WrappedIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * <p>
 * Generated by Protege (http://protege.stanford.edu). <br>
 * Source Class: Density_Dimension <br>
 * @version generated on Mon May 11 16:25:20 BST 2015 by jbjares
 */

public interface Density_Dimension extends Dimension {

    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#isDimensionOf
     */
     
    /**
     * Gets all property values for the isDimensionOf property.<p>
     * 
     * @returns a collection of values for the isDimensionOf property.
     */
    Collection<? extends WrappedIndividual> getIsDimensionOf();

    /**
     * Checks if the class has a isDimensionOf property value.<p>
     * 
     * @return true if there is a isDimensionOf property value.
     */
    boolean hasIsDimensionOf();

    /**
     * Adds a isDimensionOf property value.<p>
     * 
     * @param newIsDimensionOf the isDimensionOf property value to be added
     */
    void addIsDimensionOf(WrappedIndividual newIsDimensionOf);

    /**
     * Removes a isDimensionOf property value.<p>
     * 
     * @param oldIsDimensionOf the isDimensionOf property value to be removed.
     */
    void removeIsDimensionOf(WrappedIndividual oldIsDimensionOf);


    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasDimensionDataProperty
     */
     
    /**
     * Gets all property values for the hasDimensionDataProperty property.<p>
     * 
     * @returns a collection of values for the hasDimensionDataProperty property.
     */
    Collection<? extends Object> getHasDimensionDataProperty();

    /**
     * Checks if the class has a hasDimensionDataProperty property value.<p>
     * 
     * @return true if there is a hasDimensionDataProperty property value.
     */
    boolean hasHasDimensionDataProperty();

    /**
     * Adds a hasDimensionDataProperty property value.<p>
     * 
     * @param newHasDimensionDataProperty the hasDimensionDataProperty property value to be added
     */
    void addHasDimensionDataProperty(Object newHasDimensionDataProperty);

    /**
     * Removes a hasDimensionDataProperty property value.<p>
     * 
     * @param oldHasDimensionDataProperty the hasDimensionDataProperty property value to be removed.
     */
    void removeHasDimensionDataProperty(Object oldHasDimensionDataProperty);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
