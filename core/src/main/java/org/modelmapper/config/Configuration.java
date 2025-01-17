/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.modelmapper.config;

import org.modelmapper.Condition;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
import org.modelmapper.ResolveSourceValueInterceptor;
import org.modelmapper.spi.*;
import org.modelmapper.spi.ConditionalConverter.MatchResult;

import java.util.List;

/**
 * Configures conventions used during the matching process.
 *
 * @author Jonathan Halterman
 */
public interface Configuration {
  /**
   * The level at and below which properties can be accessed.
   */
  public enum AccessLevel {
    /** Only public properties are accessible. */
    PUBLIC,
    /** All public and protected properties are accessible. */
    PROTECTED,
    /** All public, protected and package private properties are accessible. */
    PACKAGE_PRIVATE,
    /** All properties are accessible. */
    PRIVATE;
  }

  /**
   * Registers the {@code valueReader} to use when mapping from instances of types {@code T}.
   *
   * <p>
   * This method is part of the ModelMapper SPI.
   *
   * @param <T> source type
   * @param valueReader to register
   * @throws IllegalArgumentException if {@code valueReader} is null or if type argument {@code T}
   *           is not declared for the {@code valueReader}
   */
  <T> Configuration addValueReader(ValueReader<T> valueReader);

  /**
   * Registers the {@code valueWriter} to use when mapping property to instances of types {@code T}.
   *
   * <p>
   * This method is part of the ModelMapper SPI.
   *
   * @param <T> source type
   * @param valueWriter to register
   * @throws IllegalArgumentException if {@code valueWriter} is null or if type argument {@code T}
   *           is not declared for the {@code valueWriter}
   */
  <T> Configuration addValueWriter(ValueWriter<T> valueWriter);

  /**
   * Returns a copy of the Configuration.
   */
  Configuration copy();

  /**
   * Gets the ordered list of internal conditional converters that are used to perform type
   * conversion. This list is mutable and may be modified to control which converters are used to
   * perform type conversion along with the order in which converters are selected.
   *
   * <p>
   * This method is part of the ModelMapper SPI.
   */
  List<ConditionalConverter<?, ?>> getConverters();

  /**
   * Returns the destination name tokenizer.
   *
   * @see #setDestinationNameTokenizer(NameTokenizer)
   */
  NameTokenizer getDestinationNameTokenizer();

  /**
   * Returns the destination name transformer.
   *
   * @see #setDestinationNameTransformer(NameTransformer)
   */
  NameTransformer getDestinationNameTransformer();

  /**
   * Returns the destination naming convention.
   *
   * @see #setDestinationNamingConvention(NamingConvention)
   */
  NamingConvention getDestinationNamingConvention();

  /**
   * Returns the field access level.
   *
   * @see #setFieldAccessLevel(AccessLevel)
   */
  AccessLevel getFieldAccessLevel();

  /**
   * Gets the matching strategy.
   *
   * @see #setMatchingStrategy(MatchingStrategy)
   */
  MatchingStrategy getMatchingStrategy();

  /**
   * Returns the method access level.
   *
   * @see #setMethodAccessLevel(AccessLevel)
   */
  AccessLevel getMethodAccessLevel();

  /**
   * Returns the Condition that must apply for a property in order for mapping to take place, else
   * {@code null} if no condition has been configured.
   *
   * @see #setPropertyCondition(Condition)
   */
  Condition<?, ?> getPropertyCondition();


  /**
   * Return the Interceptor for the mapping engine resolveSourceValue mapping
   * {@code null} if no interceptor has been configured.
   * @see #setResolveSourceValueInterceptor(ResolveSourceValueInterceptor)
   */
  ResolveSourceValueInterceptor<?> getResolveSourceValueInterceptor();

  /**
   * Returns the Provider used for provisioning destination object instances, else {@code null} if
   * no Provider has been configured.
   *
   * @see #setProvider(Provider)
   */
  Provider<?> getProvider();

  /**
   * Returns the source name tokenizer.
   *
   * @see #setSourceNameTokenizer(NameTokenizer)
   */
  NameTokenizer getSourceNameTokenizer();

  /**
   * Returns the source name transformer.
   *
   * @see #setSourceNameTransformer(NameTransformer)
   */
  NameTransformer getSourceNameTransformer();

  /**
   * Gets the source naming convention.
   *
   * @see #setSourceNamingConvention(NamingConvention)
   */
  NamingConvention getSourceNamingConvention();

  /**
   * Gets a thread-safe, mutable, ordered list of internal and user-defined ValueReaders that are
   * used to read source object values during mapping. This list is may be modified to control which
   * ValueReaders are used to along with the order in which ValueReaders are selected for a source
   * type.
   *
   * <p>
   * The returned List throws an IllegalArgumentException when attempting to add or set a
   * ValueReader for which the type argument {@code T} has not been defined.
   *
   * <p>
   * This method is part of the ModelMapper SPI.
   */
  List<ValueReader<?>> getValueReaders();

  /**
   * Gets a thread-safe, mutable, ordered list of internal and user-defined ValueWriters that are
   * used to write destination object values during mapping. This list is may be modified to control which
   * ValueWriters are used to along with the order in which ValueWriters are selected for a destination
   * type.
   *
   * <p>
   * The returned List throws an IllegalArgumentException when attempting to add or set a
   * ValueWriter for which the type argument {@code T} has not been defined.
   *
   * <p>
   * This method is part of the ModelMapper SPI.
   */
  List<ValueWriter<?>> getValueWriters();

  /**
   * Returns {@code true} if ambiguous properties are ignored or {@code false} if they will result
   * in an exception.
   *
   * @see #setAmbiguityIgnored(boolean)
   */
  boolean isAmbiguityIgnored();

  /**
   * Returns whether field matching is enabled.
   *
   * @see #setFieldMatchingEnabled(boolean)
   */
  boolean isFieldMatchingEnabled();

  /**
   * Returns {@code true} if {@link ConditionalConverter}s must define a {@link MatchResult#FULL
   * full} match in order to be applied. Otherwise conditional converters may also be applied for a
   * {@link MatchResult#PARTIAL partial} match.
   * <p>
   * Default is {@code false}.
   *
   * @see #setFullTypeMatchingRequired(boolean)
   */
  boolean isFullTypeMatchingRequired();

  /**
   * Returns whether implicit mapping should be enabled. When {@code true} (default), ModelMapper
   * will implicitly map source to destination properties based on configured conventions. When
   * {@code false}, only explicit mappings defined in {@link PropertyMap property maps} will be
   * used.
   *
   * @see #setImplicitMappingEnabled(boolean)
   */
  boolean isImplicitMappingEnabled();

  /**
   * Returns whether nested properties were preferred when ModelMapper were building the type map
   * with implicit mapping. When {@code true} (default), ModelMapper will prefer looking for nested
   * properties for a mapping definition.
   *
   * This option should be disabled when you are trying to map a model contains circular reference.
   *
   * <pre>
   *  modelMapper.createTypeMap(SourceTree.class, DestinationTree.class,
   *    modelMapper.getConfiguration().copy().setPreferNestedProperties(false));
   * </pre>
   *
   * @see #setPreferNestedProperties(boolean)
   */
  boolean isPreferNestedProperties();

  /**
   * Returns whether a property mapping will be skipped if the property value is {@code null}.
   * When {@code true}, ModelMapper will always not set {@code null} to destination property.
   *
   * @see #setSkipNullEnabled(boolean)
   */
  boolean isSkipNullEnabled();

  /**
   * Returns whether OSGi Class Loader Bridging is required.
   *
   * @see #setUseOSGiClassLoaderBridging(boolean)
   */
  boolean isUseOSGiClassLoaderBridging();

  /**
   * Returns whether the deep copy feature is enabled.
   *
   * @see #setDeepCopyEnabled(boolean)
   * @see org.modelmapper.internal.converter.AssignableConverter
   */
  boolean isDeepCopyEnabled();

  /**
   * Returns whether collections should be 'merged' when mapped.
   * When {@code true}, mapping a source collection of size {@code m}
   * to a destination collection of size {@code n} with {@code m < n} results
   * in a collection with the first {@code m} elements mapped from the source and elements from
   * {@code m+1} to {@code n} being preserved from the destination collection.
   * When {@code false} the elements of the destination collection are not preserved if they are not present
   * in the source collection.
   *
   * @see #setCollectionsMergeEnabled(boolean)
   * @see org.modelmapper.internal.converter.MergingCollectionConverter
   * @see org.modelmapper.internal.converter.NonMergingCollectionConverter
   */
  boolean isCollectionsMergeEnabled();

  /**
   * Sets whether destination properties that match more than one source property should be ignored.
   * When true, ambiguous destination properties are skipped during the matching process. When
   * false, a ConfigurationException is thrown when ambiguous properties are encountered.
   *
   * @param ignore whether ambiguity is to be ignored
   * @see #isAmbiguityIgnored()
   */
  Configuration setAmbiguityIgnored(boolean ignore);

  /**
   * Sets the tokenizer to be applied to destination property and class names during the matching
   * process.
   *
   * @throws IllegalArgumentException if {@code nameTokenizer} is null
   */
  Configuration setDestinationNameTokenizer(NameTokenizer nameTokenizer);

  /**
   * Sets the name transformer used to transform destination property and class names during the
   * matching process.
   *
   * @throws IllegalArgumentException if {@code nameTransformer} is null
   */
  Configuration setDestinationNameTransformer(NameTransformer nameTransformer);

  /**
   * Sets the convention used to identify destination property names during the matching process.
   *
   * @throws IllegalArgumentException if {@code namingConvention} is null
   */
  Configuration setDestinationNamingConvention(NamingConvention namingConvention);

  /**
   * Indicates that fields should be eligible for matching at the given {@code accessLevel}.
   *
   * <p>
   * <b>Note</b>: Field access is only used when {@link #setFieldMatchingEnabled(boolean) field
   * matching} is enabled.
   *
   * @throws IllegalArgumentException if {@code accessLevel} is null
   * @see #setFieldMatchingEnabled(boolean)
   */
  Configuration setFieldAccessLevel(AccessLevel accessLevel);

  /**
   * Sets whether field matching should be enabled. When true, mapping may take place between
   * accessible fields. Default is {@code false}.
   *
   * @param enabled whether field matching is enabled
   * @see #isFieldMatchingEnabled()
   * @see #setFieldAccessLevel(AccessLevel)
   */
  Configuration setFieldMatchingEnabled(boolean enabled);

  /**
   * Set whether {@link ConditionalConverter}s must define a {@link MatchResult#FULL full} match in
   * order to be applied. If {@code false}, conditional converters may also be applied for a
   * {@link MatchResult#PARTIAL partial} match.
   *
   * @param required whether full type matching is required for conditional converters.
   * @see #isFullTypeMatchingRequired()
   */
  Configuration setFullTypeMatchingRequired(boolean required);

  /**
   * Sets whether implicit mapping should be enabled. When {@code true} (default), ModelMapper will
   * implicitly map source to destination properties based on configured conventions. When
   * {@code false}, only explicit mappings defined in {@link PropertyMap property maps} will be
   * used.
   *
   * @param enabled whether implicit matching is enabled
   * @see #isImplicitMappingEnabled()
   */
  Configuration setImplicitMappingEnabled(boolean enabled);

  /**
   * Sets whether nested properties were preferred when ModelMapper were building the type map with
   * implicit mapping. When {@code true} (default), ModelMapper will prefer looking for nested
   * properties for a mapping definition.
   *
   * This option should be disabled when you are trying to map a model contains circular reference.
   *
   * <pre>
   *  modelMapper.createTypeMap(SourceTree.class, DestinationTree.class,
   *    modelMapper.getConfiguration().copy().setPreferNestedProperties(false));
   * </pre>
   *
   * @param enabled whether prefer nested properties
   * @see #isPreferNestedProperties()
   */
  Configuration setPreferNestedProperties(boolean enabled);

  /**
   * Sets whether a property should be skipped or not when the property value is {@code null}.
   *
   * @param enabled whether skip null is enabled
   * @see #isSkipNullEnabled()
   */
  Configuration setSkipNullEnabled(boolean enabled);

  /**
   * Sets whether deep copy should be enabled. When {@code false} (default), ModelMapper will
   * copy the reference to the destination object of a property if they have same type. When {@code true},
   * ModelMapper will deep copy the property to destination object.
   *
   * @param enabled enabled whether deep copy is enabled
   * @see #isDeepCopyEnabled()
   * @see org.modelmapper.internal.converter.AssignableConverter
   */
  Configuration setDeepCopyEnabled(boolean enabled);

  /**
   * Sets whether the 'merging' of collections should be enabled. When {@code true} (default), ModelMapper will
   * map the elements of the source collection to the destination collection and keep any elements of the destination collection
   * when the source collection is smaller than the destination collection.
   * When {@code false} the destination collection only consists of the elements of the source collection after mapping.
   *
   * @param enabled
   * @see #isCollectionsMergeEnabled()
   * @see org.modelmapper.internal.converter.MergingCollectionConverter
   * @see org.modelmapper.internal.converter.NonMergingCollectionConverter
   */
  Configuration setCollectionsMergeEnabled(boolean enabled);

  /**
   * Sets whether to use an OSGi Class Loader Bridge as described in the following article:
   * https://www.infoq.com/articles/code-generation-with-osgi
   * <p>
   * This eliminates the need for forcing ModelMapper users to add custom OSGi imports to cglib's internals.
   * <p>
   * In addition to that, the Class Loader Bridge will attempt to solve the issue described in the following StackOverflow topic:
   * https://stackoverflow.com/questions/47854086/cglib-creating-class-proxy-in-osgi-results-in-noclassdeffounderror
   * <p>
   * Ideally, this is expected to eliminate class loading issues in OSGi environments that were caused by missing custom imports.
   * <p>
   * <b>Note</b>:
   * If Class Loader Bridging is selected, then the source and destination class types must not be package-private.
   * That is because in Java, two classes are only consider equal to the JRE if they were loaded with the same class loader even if they otherwise represent an identical class.
   * The same is true for Java packages. If a class is loaded with a different class loader, it cannot get private-package access to any classes (or class members) that belong to another class loader.
   *
   * @param useOSGiClassLoaderBridging whether to use OSGi Class Loader Bridge. Default is false
   * @see #isUseOSGiClassLoaderBridging()
   */
  Configuration setUseOSGiClassLoaderBridging(boolean useOSGiClassLoaderBridging);

  /**
   * Sets the strategy used to match source properties to destination properties.
   *
   * @throws IllegalArgumentException if {@code matchingStrategy} is null
   */
  Configuration setMatchingStrategy(MatchingStrategy matchingStrategy);

  /**
   * Indicates that methods should be eligible for matching at the given {@code accessLevel}.
   *
   * @throws IllegalArgumentException if {@code accessLevel} is null
   * @see AccessLevel
   */
  Configuration setMethodAccessLevel(AccessLevel accessLevel);

  /**
   * Sets the {@code condition} that must apply for a property in order for mapping to take place.
   * This is overridden by any property conditions defined in a TypeMap or PropertyMap.
   *
   * @throws IllegalArgumentException if {@code condition} is null
   */
  Configuration setPropertyCondition(Condition<?, ?> condition);


  /**
   * Sets  interceptor for the mapping engine's resolveSourceValue methods.
   */
  Configuration setResolveSourceValueInterceptor(ResolveSourceValueInterceptor<?> interceptor);

  /**
   * Sets the {@code provider} to use for providing destination object instances.
   *
   * @param provider to register
   * @throws IllegalArgumentException if {@code provider} is null
   */
  Configuration setProvider(Provider<?> provider);

  /**
   * Sets the tokenizer to be applied to source property and class names during the matching
   * process.
   *
   * @throws IllegalArgumentException if {@code nameTokenizer} is null
   */
  Configuration setSourceNameTokenizer(NameTokenizer nameTokenizer);

  /**
   * Sets the name transformer used to transform source property and class names during the matching
   * process.
   *
   * @throws IllegalArgumentException if {@code nameTransformer} is null
   */
  Configuration setSourceNameTransformer(NameTransformer nameTransformer);

  /**
   * Sets the convention used to identify source property names during the matching process.
   *
   * @throws IllegalArgumentException if {@code namingConvention} is null
   */
  Configuration setSourceNamingConvention(NamingConvention namingConvention);
}
