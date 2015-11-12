package eu.sifem.model.owl.impl;

import eu.sifem.model.owl.*;

import java.util.Collection;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: DefaultVectorEntity <br>
 * @version generated on Mon May 11 16:25:20 BST 2015 by jbjares
 */
public class DefaultVectorEntity extends WrappedIndividualImpl implements VectorEntity {

    public DefaultVectorEntity(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://www.sifemontologies.com/ontologies/Simulation.owl#hasVectorValue
     */
     
    public Collection<? extends VectorValue> getHasVectorValue() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASVECTORVALUE,
                                               DefaultVectorValue.class);
    }

    public boolean hasHasVectorValue() {
	   return !getHasVectorValue().isEmpty();
    }

    public void addHasVectorValue(VectorValue newHasVectorValue) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASVECTORVALUE,
                                       newHasVectorValue);
    }

    public void removeHasVectorValue(VectorValue oldHasVectorValue) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASVECTORVALUE,
                                          oldHasVectorValue);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#alternateOf
     */
     
    public Collection<? extends Entity> getAlternateOf() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_ALTERNATEOF,
                                               DefaultEntity.class);
    }

    public boolean hasAlternateOf() {
	   return !getAlternateOf().isEmpty();
    }

    public void addAlternateOf(Entity newAlternateOf) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_ALTERNATEOF,
                                       newAlternateOf);
    }

    public void removeAlternateOf(Entity oldAlternateOf) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_ALTERNATEOF,
                                          oldAlternateOf);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#hadPrimarySource
     */
     
    public Collection<? extends Entity> getHadPrimarySource() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HADPRIMARYSOURCE,
                                               DefaultEntity.class);
    }

    public boolean hasHadPrimarySource() {
	   return !getHadPrimarySource().isEmpty();
    }

    public void addHadPrimarySource(Entity newHadPrimarySource) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HADPRIMARYSOURCE,
                                       newHadPrimarySource);
    }

    public void removeHadPrimarySource(Entity oldHadPrimarySource) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HADPRIMARYSOURCE,
                                          oldHadPrimarySource);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#qualifiedAttribution
     */
     
    public Collection<? extends Attribution> getQualifiedAttribution() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_QUALIFIEDATTRIBUTION,
                                               DefaultAttribution.class);
    }

    public boolean hasQualifiedAttribution() {
	   return !getQualifiedAttribution().isEmpty();
    }

    public void addQualifiedAttribution(Attribution newQualifiedAttribution) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_QUALIFIEDATTRIBUTION,
                                       newQualifiedAttribution);
    }

    public void removeQualifiedAttribution(Attribution oldQualifiedAttribution) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_QUALIFIEDATTRIBUTION,
                                          oldQualifiedAttribution);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#qualifiedDerivation
     */
     
    public Collection<? extends Derivation> getQualifiedDerivation() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_QUALIFIEDDERIVATION,
                                               DefaultDerivation.class);
    }

    public boolean hasQualifiedDerivation() {
	   return !getQualifiedDerivation().isEmpty();
    }

    public void addQualifiedDerivation(Derivation newQualifiedDerivation) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_QUALIFIEDDERIVATION,
                                       newQualifiedDerivation);
    }

    public void removeQualifiedDerivation(Derivation oldQualifiedDerivation) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_QUALIFIEDDERIVATION,
                                          oldQualifiedDerivation);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#qualifiedGeneration
     */
     
    public Collection<? extends Generation> getQualifiedGeneration() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_QUALIFIEDGENERATION,
                                               DefaultGeneration.class);
    }

    public boolean hasQualifiedGeneration() {
	   return !getQualifiedGeneration().isEmpty();
    }

    public void addQualifiedGeneration(Generation newQualifiedGeneration) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_QUALIFIEDGENERATION,
                                       newQualifiedGeneration);
    }

    public void removeQualifiedGeneration(Generation oldQualifiedGeneration) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_QUALIFIEDGENERATION,
                                          oldQualifiedGeneration);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#qualifiedInvalidation
     */
     
    public Collection<? extends Invalidation> getQualifiedInvalidation() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_QUALIFIEDINVALIDATION,
                                               DefaultInvalidation.class);
    }

    public boolean hasQualifiedInvalidation() {
	   return !getQualifiedInvalidation().isEmpty();
    }

    public void addQualifiedInvalidation(Invalidation newQualifiedInvalidation) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_QUALIFIEDINVALIDATION,
                                       newQualifiedInvalidation);
    }

    public void removeQualifiedInvalidation(Invalidation oldQualifiedInvalidation) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_QUALIFIEDINVALIDATION,
                                          oldQualifiedInvalidation);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#qualifiedPrimarySource
     */
     
    public Collection<? extends PrimarySource> getQualifiedPrimarySource() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_QUALIFIEDPRIMARYSOURCE,
                                               DefaultPrimarySource.class);
    }

    public boolean hasQualifiedPrimarySource() {
	   return !getQualifiedPrimarySource().isEmpty();
    }

    public void addQualifiedPrimarySource(PrimarySource newQualifiedPrimarySource) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_QUALIFIEDPRIMARYSOURCE,
                                       newQualifiedPrimarySource);
    }

    public void removeQualifiedPrimarySource(PrimarySource oldQualifiedPrimarySource) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_QUALIFIEDPRIMARYSOURCE,
                                          oldQualifiedPrimarySource);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#qualifiedQuotation
     */
     
    public Collection<? extends Quotation> getQualifiedQuotation() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_QUALIFIEDQUOTATION,
                                               DefaultQuotation.class);
    }

    public boolean hasQualifiedQuotation() {
	   return !getQualifiedQuotation().isEmpty();
    }

    public void addQualifiedQuotation(Quotation newQualifiedQuotation) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_QUALIFIEDQUOTATION,
                                       newQualifiedQuotation);
    }

    public void removeQualifiedQuotation(Quotation oldQualifiedQuotation) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_QUALIFIEDQUOTATION,
                                          oldQualifiedQuotation);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#qualifiedRevision
     */
     
    public Collection<? extends Revision> getQualifiedRevision() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_QUALIFIEDREVISION,
                                               DefaultRevision.class);
    }

    public boolean hasQualifiedRevision() {
	   return !getQualifiedRevision().isEmpty();
    }

    public void addQualifiedRevision(Revision newQualifiedRevision) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_QUALIFIEDREVISION,
                                       newQualifiedRevision);
    }

    public void removeQualifiedRevision(Revision oldQualifiedRevision) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_QUALIFIEDREVISION,
                                          oldQualifiedRevision);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#specializationOf
     */
     
    public Collection<? extends Entity> getSpecializationOf() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_SPECIALIZATIONOF,
                                               DefaultEntity.class);
    }

    public boolean hasSpecializationOf() {
	   return !getSpecializationOf().isEmpty();
    }

    public void addSpecializationOf(Entity newSpecializationOf) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_SPECIALIZATIONOF,
                                       newSpecializationOf);
    }

    public void removeSpecializationOf(Entity oldSpecializationOf) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_SPECIALIZATIONOF,
                                          oldSpecializationOf);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#wasAttributedTo
     */
     
    public Collection<? extends Agent> getWasAttributedTo() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_WASATTRIBUTEDTO,
                                               DefaultAgent.class);
    }

    public boolean hasWasAttributedTo() {
	   return !getWasAttributedTo().isEmpty();
    }

    public void addWasAttributedTo(Agent newWasAttributedTo) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_WASATTRIBUTEDTO,
                                       newWasAttributedTo);
    }

    public void removeWasAttributedTo(Agent oldWasAttributedTo) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_WASATTRIBUTEDTO,
                                          oldWasAttributedTo);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#wasDerivedFrom
     */
     
    public Collection<? extends Entity> getWasDerivedFrom() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_WASDERIVEDFROM,
                                               DefaultEntity.class);
    }

    public boolean hasWasDerivedFrom() {
	   return !getWasDerivedFrom().isEmpty();
    }

    public void addWasDerivedFrom(Entity newWasDerivedFrom) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_WASDERIVEDFROM,
                                       newWasDerivedFrom);
    }

    public void removeWasDerivedFrom(Entity oldWasDerivedFrom) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_WASDERIVEDFROM,
                                          oldWasDerivedFrom);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#wasGeneratedBy
     */
     
    public Collection<? extends Activity> getWasGeneratedBy() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_WASGENERATEDBY,
                                               DefaultActivity.class);
    }

    public boolean hasWasGeneratedBy() {
	   return !getWasGeneratedBy().isEmpty();
    }

    public void addWasGeneratedBy(Activity newWasGeneratedBy) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_WASGENERATEDBY,
                                       newWasGeneratedBy);
    }

    public void removeWasGeneratedBy(Activity oldWasGeneratedBy) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_WASGENERATEDBY,
                                          oldWasGeneratedBy);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#wasInvalidatedBy
     */
     
    public Collection<? extends Activity> getWasInvalidatedBy() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_WASINVALIDATEDBY,
                                               DefaultActivity.class);
    }

    public boolean hasWasInvalidatedBy() {
	   return !getWasInvalidatedBy().isEmpty();
    }

    public void addWasInvalidatedBy(Activity newWasInvalidatedBy) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_WASINVALIDATEDBY,
                                       newWasInvalidatedBy);
    }

    public void removeWasInvalidatedBy(Activity oldWasInvalidatedBy) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_WASINVALIDATEDBY,
                                          oldWasInvalidatedBy);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#wasQuotedFrom
     */
     
    public Collection<? extends Entity> getWasQuotedFrom() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_WASQUOTEDFROM,
                                               DefaultEntity.class);
    }

    public boolean hasWasQuotedFrom() {
	   return !getWasQuotedFrom().isEmpty();
    }

    public void addWasQuotedFrom(Entity newWasQuotedFrom) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_WASQUOTEDFROM,
                                       newWasQuotedFrom);
    }

    public void removeWasQuotedFrom(Entity oldWasQuotedFrom) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_WASQUOTEDFROM,
                                          oldWasQuotedFrom);
    }


    /* ***************************************************
     * Object Property http://www.w3.org/ns/prov#wasRevisionOf
     */
     
    public Collection<? extends Entity> getWasRevisionOf() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_WASREVISIONOF,
                                               DefaultEntity.class);
    }

    public boolean hasWasRevisionOf() {
	   return !getWasRevisionOf().isEmpty();
    }

    public void addWasRevisionOf(Entity newWasRevisionOf) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_WASREVISIONOF,
                                       newWasRevisionOf);
    }

    public void removeWasRevisionOf(Entity oldWasRevisionOf) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_WASREVISIONOF,
                                          oldWasRevisionOf);
    }


    /* ***************************************************
     * Data Property http://www.w3.org/ns/prov#generatedAtTime
     */
     
    public Collection<? extends Object> getGeneratedAtTime() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_GENERATEDATTIME, Object.class);
    }

    public boolean hasGeneratedAtTime() {
		return !getGeneratedAtTime().isEmpty();
    }

    public void addGeneratedAtTime(Object newGeneratedAtTime) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_GENERATEDATTIME, newGeneratedAtTime);
    }

    public void removeGeneratedAtTime(Object oldGeneratedAtTime) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_GENERATEDATTIME, oldGeneratedAtTime);
    }


    /* ***************************************************
     * Data Property http://www.w3.org/ns/prov#invalidatedAtTime
     */
     
    public Collection<? extends Object> getInvalidatedAtTime() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVALIDATEDATTIME, Object.class);
    }

    public boolean hasInvalidatedAtTime() {
		return !getInvalidatedAtTime().isEmpty();
    }

    public void addInvalidatedAtTime(Object newInvalidatedAtTime) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVALIDATEDATTIME, newInvalidatedAtTime);
    }

    public void removeInvalidatedAtTime(Object oldInvalidatedAtTime) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVALIDATEDATTIME, oldInvalidatedAtTime);
    }


    /* ***************************************************
     * Data Property http://www.w3.org/ns/prov#value
     */
     
    public Collection<? extends Object> getValue() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_VALUE, Object.class);
    }

    public boolean hasValue() {
		return !getValue().isEmpty();
    }

    public void addValue(Object newValue) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_VALUE, newValue);
    }

    public void removeValue(Object oldValue) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_VALUE, oldValue);
    }


}
