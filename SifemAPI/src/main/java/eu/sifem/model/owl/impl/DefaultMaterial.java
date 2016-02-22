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
 * Source Class: DefaultMaterial <br>
 * @version generated on Mon May 11 16:25:20 BST 2015 by jbjares
 */
public class DefaultMaterial extends WrappedIndividualImpl implements Material {

    public DefaultMaterial(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#hasMaterialSettings
     */
     
    public Collection<? extends MaterialSettings> getHasMaterialSettings() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASMATERIALSETTINGS,
                                               DefaultMaterialSettings.class);
    }

    public boolean hasHasMaterialSettings() {
	   return !getHasMaterialSettings().isEmpty();
    }

    public void addHasMaterialSettings(MaterialSettings newHasMaterialSettings) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASMATERIALSETTINGS,
                                       newHasMaterialSettings);
    }

    public void removeHasMaterialSettings(MaterialSettings oldHasMaterialSettings) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASMATERIALSETTINGS,
                                          oldHasMaterialSettings);
    }


    /* ***************************************************
     * Object Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#isMaterialOf
     */
     
    public Collection<? extends Subdomain_group> getIsMaterialOf() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_ISMATERIALOF,
                                               DefaultSubdomain_group.class);
    }

    public boolean hasIsMaterialOf() {
	   return !getIsMaterialOf().isEmpty();
    }

    public void addIsMaterialOf(Subdomain_group newIsMaterialOf) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_ISMATERIALOF,
                                       newIsMaterialOf);
    }

    public void removeIsMaterialOf(Subdomain_group oldIsMaterialOf) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_ISMATERIALOF,
                                          oldIsMaterialOf);
    }


}
