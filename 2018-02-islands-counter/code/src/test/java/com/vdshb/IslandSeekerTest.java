package com.vdshb;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IslandSeekerTest {

    private IslandsCounter underTest;

    @BeforeEach
    public void setUp() {
        underTest = new IslandsCounter();
    }

    @Test
    public void should_handle_the_simplest_map() throws IOException {
        String source = "0";
        InputStream in = IOUtils.toInputStream(source, StandardCharsets.UTF_8);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

        int result = underTest.countIslands(reader);

        assertEquals(0, result);
    }

    @Test
    public void should_count_zero_islands_in_empty_map() throws IOException {
        String source = "00\n00";
        InputStream in = IOUtils.toInputStream(source, StandardCharsets.UTF_8);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

        int result = underTest.countIslands(reader);

        assertEquals(0, result);
    }

    @Test
    public void should_count_simple_island() throws IOException {
        String source = "11\n00";
        InputStream in = IOUtils.toInputStream(source, StandardCharsets.UTF_8);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

        int result = underTest.countIslands(reader);

        assertEquals(1, result);
    }

    @Test
    public void should_count_simple_island_in_any_line() throws IOException {
        String source = "00\n11";
        InputStream in = IOUtils.toInputStream(source, StandardCharsets.UTF_8);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

        int result = underTest.countIslands(reader);

        assertEquals(1, result);
    }

    @Test
    public void should_count_islands() throws IOException {
        String source = "" +
                "000101000\n" +
                "100101001\n" +
                "000111001\n" +
                "110101001";
        InputStream in = IOUtils.toInputStream(source, StandardCharsets.UTF_8);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

        int result = underTest.countIslands(reader);

        assertEquals(3, result);
    }

    @Test
    public void should_count_islands_from_file_map() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("map.txt").getFile());

        try (Reader reader = new BufferedReader(new FileReader(file),16384)) {
            int result = underTest.countIslands(reader);
            assertEquals(3 * (4), result);
        }
    }
}
