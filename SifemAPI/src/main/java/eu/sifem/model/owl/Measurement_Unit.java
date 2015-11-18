package eu.sifem.model.owl;

import java.util.Collection;

import org.protege.owl.codegeneration.WrappedIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * <p>
 * Generated by Protege (http://protege.stanford.edu). <br>
 * Source Class: Measurement_Unit <br>
 * @version generated on Mon May 11 16:25:20 BST 2015 by jbjares
 */

public interface Measurement_Unit extends WrappedIndividual {

    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#isMeasurementUnitOf
     */
     
    /**
     * Gets all property values for the isMeasurementUnitOf property.<p>
     * 
     * @returns a collection of values for the isMeasurementUnitOf property.
     */
    Collection<? extends WrappedIndividual> getIsMeasurementUnitOf();

    /**
     * Checks if the class has a isMeasurementUnitOf property value.<p>
     * 
     * @return true if there is a isMeasurementUnitOf property value.
     */
    boolean hasIsMeasurementUnitOf();

    /**
     * Adds a isMeasurementUnitOf property value.<p>
     * 
     * @param newIsMeasurementUnitOf the isMeasurementUnitOf property value to be added
     */
    void addIsMeasurementUnitOf(WrappedIndividual newIsMeasurementUnitOf);

    /**
     * Removes a isMeasurementUnitOf property value.<p>
     * 
     * @param oldIsMeasurementUnitOf the isMeasurementUnitOf property value to be removed.
     */
    void removeIsMeasurementUnitOf(WrappedIndividual oldIsMeasurementUnitOf);


    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasMeasurementUnitValue
     */
     
    /**
     * Gets all property values for the hasMeasurementUnitValue property.<p>
     * 
     * @returns a collection of values for the hasMeasurementUnitValue property.
     */
    Collection<? extends String> getHasMeasurementUnitValue();

    /**
     * Checks if the class has a hasMeasurementUnitValue property value.<p>
     * 
     * @return true if there is a hasMeasurementUnitValue property value.
     */
    boolean hasHasMeasurementUnitValue();

    /**
     * Adds a hasMeasurementUnitValue property value.<p>
     * 
     * @param newHasMeasurementUnitValue the hasMeasurementUnitValue property value to be added
     */
    void addHasMeasurementUnitValue(String newHasMeasurementUnitValue);

    /**
     * Removes a hasMeasurementUnitValue property value.<p>
     * 
     * @param oldHasMeasurementUnitValue the hasMeasurementUnitValue property value to be removed.
     */
    void removeHasMeasurementUnitValue(String oldHasMeasurementUnitValue);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}