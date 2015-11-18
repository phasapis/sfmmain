package eu.sifem.model.owl;

import java.util.Collection;

import org.protege.owl.codegeneration.WrappedIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * <p>
 * Generated by Protege (http://protege.stanford.edu). <br>
 * Source Class: VectorEntity <br>
 * @version generated on Mon May 11 16:25:20 BST 2015 by jbjares
 */

public interface VectorEntity extends Entity {

    /* ***************************************************
     * Property http://www.sifemontologies.com/ontologies/Simulation.owl#hasVectorValue
     */
     
    /**
     * Gets all property values for the hasVectorValue property.<p>
     * 
     * @returns a collection of values for the hasVectorValue property.
     */
    Collection<? extends VectorValue> getHasVectorValue();

    /**
     * Checks if the class has a hasVectorValue property value.<p>
     * 
     * @return true if there is a hasVectorValue property value.
     */
    boolean hasHasVectorValue();

    /**
     * Adds a hasVectorValue property value.<p>
     * 
     * @param newHasVectorValue the hasVectorValue property value to be added
     */
    void addHasVectorValue(VectorValue newHasVectorValue);

    /**
     * Removes a hasVectorValue property value.<p>
     * 
     * @param oldHasVectorValue the hasVectorValue property value to be removed.
     */
    void removeHasVectorValue(VectorValue oldHasVectorValue);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#alternateOf
     */
     
    /**
     * Gets all property values for the alternateOf property.<p>
     * 
     * @returns a collection of values for the alternateOf property.
     */
    Collection<? extends Entity> getAlternateOf();

    /**
     * Checks if the class has a alternateOf property value.<p>
     * 
     * @return true if there is a alternateOf property value.
     */
    boolean hasAlternateOf();

    /**
     * Adds a alternateOf property value.<p>
     * 
     * @param newAlternateOf the alternateOf property value to be added
     */
    void addAlternateOf(Entity newAlternateOf);

    /**
     * Removes a alternateOf property value.<p>
     * 
     * @param oldAlternateOf the alternateOf property value to be removed.
     */
    void removeAlternateOf(Entity oldAlternateOf);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#hadPrimarySource
     */
     
    /**
     * Gets all property values for the hadPrimarySource property.<p>
     * 
     * @returns a collection of values for the hadPrimarySource property.
     */
    Collection<? extends Entity> getHadPrimarySource();

    /**
     * Checks if the class has a hadPrimarySource property value.<p>
     * 
     * @return true if there is a hadPrimarySource property value.
     */
    boolean hasHadPrimarySource();

    /**
     * Adds a hadPrimarySource property value.<p>
     * 
     * @param newHadPrimarySource the hadPrimarySource property value to be added
     */
    void addHadPrimarySource(Entity newHadPrimarySource);

    /**
     * Removes a hadPrimarySource property value.<p>
     * 
     * @param oldHadPrimarySource the hadPrimarySource property value to be removed.
     */
    void removeHadPrimarySource(Entity oldHadPrimarySource);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedAttribution
     */
     
    /**
     * Gets all property values for the qualifiedAttribution property.<p>
     * 
     * @returns a collection of values for the qualifiedAttribution property.
     */
    Collection<? extends Attribution> getQualifiedAttribution();

    /**
     * Checks if the class has a qualifiedAttribution property value.<p>
     * 
     * @return true if there is a qualifiedAttribution property value.
     */
    boolean hasQualifiedAttribution();

    /**
     * Adds a qualifiedAttribution property value.<p>
     * 
     * @param newQualifiedAttribution the qualifiedAttribution property value to be added
     */
    void addQualifiedAttribution(Attribution newQualifiedAttribution);

    /**
     * Removes a qualifiedAttribution property value.<p>
     * 
     * @param oldQualifiedAttribution the qualifiedAttribution property value to be removed.
     */
    void removeQualifiedAttribution(Attribution oldQualifiedAttribution);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedDerivation
     */
     
    /**
     * Gets all property values for the qualifiedDerivation property.<p>
     * 
     * @returns a collection of values for the qualifiedDerivation property.
     */
    Collection<? extends Derivation> getQualifiedDerivation();

    /**
     * Checks if the class has a qualifiedDerivation property value.<p>
     * 
     * @return true if there is a qualifiedDerivation property value.
     */
    boolean hasQualifiedDerivation();

    /**
     * Adds a qualifiedDerivation property value.<p>
     * 
     * @param newQualifiedDerivation the qualifiedDerivation property value to be added
     */
    void addQualifiedDerivation(Derivation newQualifiedDerivation);

    /**
     * Removes a qualifiedDerivation property value.<p>
     * 
     * @param oldQualifiedDerivation the qualifiedDerivation property value to be removed.
     */
    void removeQualifiedDerivation(Derivation oldQualifiedDerivation);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedGeneration
     */
     
    /**
     * Gets all property values for the qualifiedGeneration property.<p>
     * 
     * @returns a collection of values for the qualifiedGeneration property.
     */
    Collection<? extends Generation> getQualifiedGeneration();

    /**
     * Checks if the class has a qualifiedGeneration property value.<p>
     * 
     * @return true if there is a qualifiedGeneration property value.
     */
    boolean hasQualifiedGeneration();

    /**
     * Adds a qualifiedGeneration property value.<p>
     * 
     * @param newQualifiedGeneration the qualifiedGeneration property value to be added
     */
    void addQualifiedGeneration(Generation newQualifiedGeneration);

    /**
     * Removes a qualifiedGeneration property value.<p>
     * 
     * @param oldQualifiedGeneration the qualifiedGeneration property value to be removed.
     */
    void removeQualifiedGeneration(Generation oldQualifiedGeneration);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedInvalidation
     */
     
    /**
     * Gets all property values for the qualifiedInvalidation property.<p>
     * 
     * @returns a collection of values for the qualifiedInvalidation property.
     */
    Collection<? extends Invalidation> getQualifiedInvalidation();

    /**
     * Checks if the class has a qualifiedInvalidation property value.<p>
     * 
     * @return true if there is a qualifiedInvalidation property value.
     */
    boolean hasQualifiedInvalidation();

    /**
     * Adds a qualifiedInvalidation property value.<p>
     * 
     * @param newQualifiedInvalidation the qualifiedInvalidation property value to be added
     */
    void addQualifiedInvalidation(Invalidation newQualifiedInvalidation);

    /**
     * Removes a qualifiedInvalidation property value.<p>
     * 
     * @param oldQualifiedInvalidation the qualifiedInvalidation property value to be removed.
     */
    void removeQualifiedInvalidation(Invalidation oldQualifiedInvalidation);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedPrimarySource
     */
     
    /**
     * Gets all property values for the qualifiedPrimarySource property.<p>
     * 
     * @returns a collection of values for the qualifiedPrimarySource property.
     */
    Collection<? extends PrimarySource> getQualifiedPrimarySource();

    /**
     * Checks if the class has a qualifiedPrimarySource property value.<p>
     * 
     * @return true if there is a qualifiedPrimarySource property value.
     */
    boolean hasQualifiedPrimarySource();

    /**
     * Adds a qualifiedPrimarySource property value.<p>
     * 
     * @param newQualifiedPrimarySource the qualifiedPrimarySource property value to be added
     */
    void addQualifiedPrimarySource(PrimarySource newQualifiedPrimarySource);

    /**
     * Removes a qualifiedPrimarySource property value.<p>
     * 
     * @param oldQualifiedPrimarySource the qualifiedPrimarySource property value to be removed.
     */
    void removeQualifiedPrimarySource(PrimarySource oldQualifiedPrimarySource);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedQuotation
     */
     
    /**
     * Gets all property values for the qualifiedQuotation property.<p>
     * 
     * @returns a collection of values for the qualifiedQuotation property.
     */
    Collection<? extends Quotation> getQualifiedQuotation();

    /**
     * Checks if the class has a qualifiedQuotation property value.<p>
     * 
     * @return true if there is a qualifiedQuotation property value.
     */
    boolean hasQualifiedQuotation();

    /**
     * Adds a qualifiedQuotation property value.<p>
     * 
     * @param newQualifiedQuotation the qualifiedQuotation property value to be added
     */
    void addQualifiedQuotation(Quotation newQualifiedQuotation);

    /**
     * Removes a qualifiedQuotation property value.<p>
     * 
     * @param oldQualifiedQuotation the qualifiedQuotation property value to be removed.
     */
    void removeQualifiedQuotation(Quotation oldQualifiedQuotation);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#qualifiedRevision
     */
     
    /**
     * Gets all property values for the qualifiedRevision property.<p>
     * 
     * @returns a collection of values for the qualifiedRevision property.
     */
    Collection<? extends Revision> getQualifiedRevision();

    /**
     * Checks if the class has a qualifiedRevision property value.<p>
     * 
     * @return true if there is a qualifiedRevision property value.
     */
    boolean hasQualifiedRevision();

    /**
     * Adds a qualifiedRevision property value.<p>
     * 
     * @param newQualifiedRevision the qualifiedRevision property value to be added
     */
    void addQualifiedRevision(Revision newQualifiedRevision);

    /**
     * Removes a qualifiedRevision property value.<p>
     * 
     * @param oldQualifiedRevision the qualifiedRevision property value to be removed.
     */
    void removeQualifiedRevision(Revision oldQualifiedRevision);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#specializationOf
     */
     
    /**
     * Gets all property values for the specializationOf property.<p>
     * 
     * @returns a collection of values for the specializationOf property.
     */
    Collection<? extends Entity> getSpecializationOf();

    /**
     * Checks if the class has a specializationOf property value.<p>
     * 
     * @return true if there is a specializationOf property value.
     */
    boolean hasSpecializationOf();

    /**
     * Adds a specializationOf property value.<p>
     * 
     * @param newSpecializationOf the specializationOf property value to be added
     */
    void addSpecializationOf(Entity newSpecializationOf);

    /**
     * Removes a specializationOf property value.<p>
     * 
     * @param oldSpecializationOf the specializationOf property value to be removed.
     */
    void removeSpecializationOf(Entity oldSpecializationOf);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasAttributedTo
     */
     
    /**
     * Gets all property values for the wasAttributedTo property.<p>
     * 
     * @returns a collection of values for the wasAttributedTo property.
     */
    Collection<? extends Agent> getWasAttributedTo();

    /**
     * Checks if the class has a wasAttributedTo property value.<p>
     * 
     * @return true if there is a wasAttributedTo property value.
     */
    boolean hasWasAttributedTo();

    /**
     * Adds a wasAttributedTo property value.<p>
     * 
     * @param newWasAttributedTo the wasAttributedTo property value to be added
     */
    void addWasAttributedTo(Agent newWasAttributedTo);

    /**
     * Removes a wasAttributedTo property value.<p>
     * 
     * @param oldWasAttributedTo the wasAttributedTo property value to be removed.
     */
    void removeWasAttributedTo(Agent oldWasAttributedTo);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasDerivedFrom
     */
     
    /**
     * Gets all property values for the wasDerivedFrom property.<p>
     * 
     * @returns a collection of values for the wasDerivedFrom property.
     */
    Collection<? extends Entity> getWasDerivedFrom();

    /**
     * Checks if the class has a wasDerivedFrom property value.<p>
     * 
     * @return true if there is a wasDerivedFrom property value.
     */
    boolean hasWasDerivedFrom();

    /**
     * Adds a wasDerivedFrom property value.<p>
     * 
     * @param newWasDerivedFrom the wasDerivedFrom property value to be added
     */
    void addWasDerivedFrom(Entity newWasDerivedFrom);

    /**
     * Removes a wasDerivedFrom property value.<p>
     * 
     * @param oldWasDerivedFrom the wasDerivedFrom property value to be removed.
     */
    void removeWasDerivedFrom(Entity oldWasDerivedFrom);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasGeneratedBy
     */
     
    /**
     * Gets all property values for the wasGeneratedBy property.<p>
     * 
     * @returns a collection of values for the wasGeneratedBy property.
     */
    Collection<? extends Activity> getWasGeneratedBy();

    /**
     * Checks if the class has a wasGeneratedBy property value.<p>
     * 
     * @return true if there is a wasGeneratedBy property value.
     */
    boolean hasWasGeneratedBy();

    /**
     * Adds a wasGeneratedBy property value.<p>
     * 
     * @param newWasGeneratedBy the wasGeneratedBy property value to be added
     */
    void addWasGeneratedBy(Activity newWasGeneratedBy);

    /**
     * Removes a wasGeneratedBy property value.<p>
     * 
     * @param oldWasGeneratedBy the wasGeneratedBy property value to be removed.
     */
    void removeWasGeneratedBy(Activity oldWasGeneratedBy);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasInvalidatedBy
     */
     
    /**
     * Gets all property values for the wasInvalidatedBy property.<p>
     * 
     * @returns a collection of values for the wasInvalidatedBy property.
     */
    Collection<? extends Activity> getWasInvalidatedBy();

    /**
     * Checks if the class has a wasInvalidatedBy property value.<p>
     * 
     * @return true if there is a wasInvalidatedBy property value.
     */
    boolean hasWasInvalidatedBy();

    /**
     * Adds a wasInvalidatedBy property value.<p>
     * 
     * @param newWasInvalidatedBy the wasInvalidatedBy property value to be added
     */
    void addWasInvalidatedBy(Activity newWasInvalidatedBy);

    /**
     * Removes a wasInvalidatedBy property value.<p>
     * 
     * @param oldWasInvalidatedBy the wasInvalidatedBy property value to be removed.
     */
    void removeWasInvalidatedBy(Activity oldWasInvalidatedBy);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasQuotedFrom
     */
     
    /**
     * Gets all property values for the wasQuotedFrom property.<p>
     * 
     * @returns a collection of values for the wasQuotedFrom property.
     */
    Collection<? extends Entity> getWasQuotedFrom();

    /**
     * Checks if the class has a wasQuotedFrom property value.<p>
     * 
     * @return true if there is a wasQuotedFrom property value.
     */
    boolean hasWasQuotedFrom();

    /**
     * Adds a wasQuotedFrom property value.<p>
     * 
     * @param newWasQuotedFrom the wasQuotedFrom property value to be added
     */
    void addWasQuotedFrom(Entity newWasQuotedFrom);

    /**
     * Removes a wasQuotedFrom property value.<p>
     * 
     * @param oldWasQuotedFrom the wasQuotedFrom property value to be removed.
     */
    void removeWasQuotedFrom(Entity oldWasQuotedFrom);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#wasRevisionOf
     */
     
    /**
     * Gets all property values for the wasRevisionOf property.<p>
     * 
     * @returns a collection of values for the wasRevisionOf property.
     */
    Collection<? extends Entity> getWasRevisionOf();

    /**
     * Checks if the class has a wasRevisionOf property value.<p>
     * 
     * @return true if there is a wasRevisionOf property value.
     */
    boolean hasWasRevisionOf();

    /**
     * Adds a wasRevisionOf property value.<p>
     * 
     * @param newWasRevisionOf the wasRevisionOf property value to be added
     */
    void addWasRevisionOf(Entity newWasRevisionOf);

    /**
     * Removes a wasRevisionOf property value.<p>
     * 
     * @param oldWasRevisionOf the wasRevisionOf property value to be removed.
     */
    void removeWasRevisionOf(Entity oldWasRevisionOf);


    /* ***************************************************
     * Property http://www.w3.org/ns/prov#generatedAtTime
     */
     
    /**
     * Gets all property values for the generatedAtTime property.<p>
     * 
     * @returns a collection of values for the generatedAtTime property.
     */
    Collection<? extends Object> getGeneratedAtTime();

    /**
     * Checks if the class has a generatedAtTime property value.<p>
     * 
     * @return true if there is a generatedAtTime property value.
     */
    boolean hasGeneratedAtTime();

    /**
     * Adds a generatedAtTime property value.<p>
     * 
     * @param newGeneratedAtTime the generatedAtTime property value to be added
     */
    void addGeneratedAtTime(Object newGeneratedAtTime);

    /**
     * Removes a generatedAtTime property value.<p>
     * 
     * @param oldGeneratedAtTime the generatedAtTime property value to be removed.
     */
    void removeGeneratedAtTime(Object oldGeneratedAtTime);



    /* ***************************************************
     * Property http://www.w3.org/ns/prov#invalidatedAtTime
     */
     
    /**
     * Gets all property values for the invalidatedAtTime property.<p>
     * 
     * @returns a collection of values for the invalidatedAtTime property.
     */
    Collection<? extends Object> getInvalidatedAtTime();

    /**
     * Checks if the class has a invalidatedAtTime property value.<p>
     * 
     * @return true if there is a invalidatedAtTime property value.
     */
    boolean hasInvalidatedAtTime();

    /**
     * Adds a invalidatedAtTime property value.<p>
     * 
     * @param newInvalidatedAtTime the invalidatedAtTime property value to be added
     */
    void addInvalidatedAtTime(Object newInvalidatedAtTime);

    /**
     * Removes a invalidatedAtTime property value.<p>
     * 
     * @param oldInvalidatedAtTime the invalidatedAtTime property value to be removed.
     */
    void removeInvalidatedAtTime(Object oldInvalidatedAtTime);



    /* ***************************************************
     * Property http://www.w3.org/ns/prov#value
     */
     
    /**
     * Gets all property values for the value property.<p>
     * 
     * @returns a collection of values for the value property.
     */
    Collection<? extends Object> getValue();

    /**
     * Checks if the class has a value property value.<p>
     * 
     * @return true if there is a value property value.
     */
    boolean hasValue();

    /**
     * Adds a value property value.<p>
     * 
     * @param newValue the value property value to be added
     */
    void addValue(Object newValue);

    /**
     * Removes a value property value.<p>
     * 
     * @param oldValue the value property value to be removed.
     */
    void removeValue(Object oldValue);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}