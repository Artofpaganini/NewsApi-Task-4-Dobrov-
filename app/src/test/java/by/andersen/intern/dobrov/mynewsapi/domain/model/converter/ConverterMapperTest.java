package by.andersen.intern.dobrov.mynewsapi.domain.model.converter;

import junit.framework.TestCase;

import org.mockito.Mock;

import by.andersen.intern.dobrov.mynewsapi.domain.model.Source;

public class ConverterMapperTest extends TestCase {

    public  String test_source = "{\"id\":\"1\",\"name\":\"someName\"}";
    private ConverterMapper converterMapper;

    @Mock
    private Source source1;

    public void setUp() throws Exception {
        super.setUp();
        converterMapper = new ConverterMapper();

        source1 = new Source();
    }


    public void test_ShouldConvertFromSourceToString() {
        source1.setId("1");
        source1.setName("someName");

        assertEquals(test_source, converterMapper.fromSource(source1));


    }

    public void test_ShouldConvertFromStringToSource() {
        source1.setId("1");
        source1.setName("someName");

        Source source2 = converterMapper.toSource(test_source);

        assertEquals(source1.getId(), source2.getId());
        assertEquals(source1.getName(), source2.getName());

    }
}