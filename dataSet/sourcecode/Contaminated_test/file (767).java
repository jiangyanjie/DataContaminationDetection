package ar.com.mtmte.core.type.transformer;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import ar.com.mtmte.core.test1.Entity1A;

public class DefaultTransformerTest {
	private static final String SELECTED_VALUE = "true";
	private static final String SELECTED_FIELD = "selected";
	private static final String ENTITY_NAME_FIELD = "name";
	private static final String ENTITY_NAME_VALUE = "entityName";

	private static final String ENTITY_CODE_FIELD = "code";
	private static final String ENTITY_CODE_VALUE = "codeName";
	private static final String CHANGED_VALUE = "changed";
	private static final String CHANGED_FIELD = "false";

	@SuppressWarnings("unchecked")
	@Test
	public void testDefaultTransformerWithDefinedSimpleFieldShouldCopyTheValueToSameNameProperty() {
		Entity1A entity1A = new Entity1A();
		entity1A.setName(ENTITY_NAME_VALUE);

		DefaultTransformer entity1ATransformer = new DefaultTransformer();

		Object transformed = entity1ATransformer.transform(entity1A);

		Map<String, Object> transformedEntity = (Map<String, Object>) transformed;
		assertEquals(ENTITY_NAME_VALUE, transformedEntity.get(ENTITY_NAME_FIELD));// copy
																					// value
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDefaultTransformerWithDefinedSimpleFieldWithTwoFieldsShouldCopyTheValueToSameNameProperty() {
		Entity1A entity1A = new Entity1A();
		entity1A.setName(ENTITY_NAME_VALUE);
		entity1A.setCode(ENTITY_CODE_VALUE);

		DefaultTransformer entity1ATransformer = new DefaultTransformer();

		Object transformed = entity1ATransformer.transform(entity1A);

		Map<String, Object> transformedEntity = (Map<String, Object>) transformed;
		assertEquals(ENTITY_NAME_VALUE, transformedEntity.get(ENTITY_NAME_FIELD));// copy
																					// value
		assertEquals(ENTITY_CODE_VALUE, transformedEntity.get(ENTITY_CODE_FIELD));// copy
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDefaultTransformerWithDefinedSimpleFieldAndNewFieldShouldCopyTheValueToSameNamePropertyAndCreateTheNewField() {
		Entity1A entity1A = new Entity1A();
		entity1A.setName(ENTITY_NAME_VALUE);

		DefaultTransformer entity1ATransformer = new DefaultTransformer();
		entity1ATransformer.defineNewField(SELECTED_FIELD, SELECTED_VALUE);

		Object transformed = entity1ATransformer.transform(entity1A);

		Map<String, Object> transformedEntity = (Map<String, Object>) transformed;
		assertEquals(ENTITY_NAME_VALUE, transformedEntity.get(ENTITY_NAME_FIELD));// copy
																					// value
		assertEquals(SELECTED_VALUE, transformedEntity.get(SELECTED_FIELD));// create new field
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDefaultTransformerWithDefinedSimpleFieldAndTwoNewFieldsShouldCopyTheValueToSameNamePropertyAndCreateTheNewFields() {
		Entity1A entity1A = new Entity1A();
		entity1A.setName(ENTITY_NAME_VALUE);

		DefaultTransformer entity1ATransformer = new DefaultTransformer();
		entity1ATransformer.defineNewField(SELECTED_FIELD, SELECTED_VALUE);
		entity1ATransformer.defineNewField(CHANGED_FIELD, CHANGED_VALUE);

		Object transformed = entity1ATransformer.transform(entity1A);

		Map<String, Object> transformedEntity = (Map<String, Object>) transformed;
		assertEquals(ENTITY_NAME_VALUE, transformedEntity.get(ENTITY_NAME_FIELD));// copy
																					// value
		assertEquals(SELECTED_VALUE, transformedEntity.get(SELECTED_FIELD));// create new field
		assertEquals(CHANGED_VALUE, transformedEntity.get(CHANGED_FIELD));// create new field
	}
}
