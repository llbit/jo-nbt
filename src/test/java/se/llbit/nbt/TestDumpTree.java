/* Copyright (c) 2017, Jesper Öqvist
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package se.llbit.nbt;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestDumpTree {

  private static InputStream openInputStream(String filename) throws IOException {
    return new BufferedInputStream(new FileInputStream(new File(filename)));
  }

  protected static Tag readGzipped(String filename) throws IOException {
    try (DataInputStream in = openGzipInputStream(filename)) {
      return NamedTag.read(in);
    } catch (IOException e) {
      System.err.println("Failed to read NBT file: " + filename);
      throw e;
    }
  }

  private static DataInputStream openGzipInputStream(String filename) throws IOException {
    return new DataInputStream(new GZIPInputStream(openInputStream(filename)));
  }

  /** Read a Minecraft level.dat file and test that it has the expected structure. */
  @Test public void testLevelDat1() throws IOException {
    Tag root = readGzipped("testfiles/level.dat");
    List<String> lines =
        Files.readAllLines(Paths.get("testfiles/level.dump"), StandardCharsets.UTF_8);
    String actual = root.dumpTree();
    StringBuilder expected = new StringBuilder(actual.length());
    for (String line : lines) {
      expected.append(line);
      expected.append("\n");
    }
    assertEquals(expected.toString(), actual);
  }

  @Test public void testChunk() throws IOException {
    Tag root = readGzipped("testfiles/chunk.dat");
    List<String> lines =
        Files.readAllLines(Paths.get("testfiles/chunk.dump"), StandardCharsets.UTF_8);
    String actual = root.dumpTree();
    StringBuilder expected = new StringBuilder(actual.length());
    for (String line : lines) {
      expected.append(line);
      expected.append("\n");
    }
    assertEquals(expected.toString(), actual);
  }

  /** Test that reading a writing an NBT tree to disk preserves the contents. */
  @Test public void testRoundTrip1() throws IOException {
    Tag root = readGzipped("testfiles/level.dat");
    try (DataOutputStream out = new DataOutputStream(
        new GZIPOutputStream(new FileOutputStream("testfiles/level.dat.out")))) {
      root.write(out);
    }
    Tag root2 = readGzipped("testfiles/level.dat.out");
    assertEquals(root.dumpTree(), root2.dumpTree());
  }

  @Test public void testRoundTrip2() throws IOException {
    Tag root = readGzipped("testfiles/chunk.dat");
    try (DataOutputStream out = new DataOutputStream(
        new GZIPOutputStream(new FileOutputStream("testfiles/chunk.dat.out")))) {
      root.write(out);
    }
    Tag root2 = readGzipped("testfiles/chunk.dat.out");
    assertEquals(root.dumpTree(), root2.dumpTree());
  }
}
