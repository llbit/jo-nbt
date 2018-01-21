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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestParsing {
  /** Read a Minecraft level.dat file and test that it has the expected structure. */
  @Test public void testLevelDat1() throws IOException {
    Tag root = FileUtils.readGzipped("testfiles/level.dat");
    assertTrue(root.isNamed(""));
    Tag data = root.unpack();
    assertTrue(data.isCompoundTag());
    assertEquals("TAG_Compound", data.type());
    assertEquals("TAG_Compound", data.name());
    assertTrue(data.get("Data").isCompoundTag());
  }

  /** Partial parsing skips over tags that are not requested. */
  @Test public void testPartial1() throws IOException {
    try (DataInputStream in = FileUtils.openGzipInputStream("testfiles/level.dat")) {
      Set<String> request = new HashSet<>();
      request.add(".Data.Version.Name");
      request.add(".Data.DimensionData.1.DragonFight.Gateways");
      request.add(".Data.Player.Attributes.3.Name"); // List indexing.
      Map<String, Tag> result = NamedTag.quickParse(in, request);
      assertEquals("17w13b", result.get(".Data.Version.Name").stringValue());
      assertEquals(true, result.get(".Data.DimensionData.1.DragonFight.Gateways").isList());
      assertEquals("generic.armor", result.get(".Data.Player.Attributes.3.Name").stringValue());
    }
  }

  /** List items can be accessed by index. */
  @Test public void testPartial2() throws IOException {
    try (DataInputStream in = FileUtils.openGzipInputStream("testfiles/level.dat")) {
      Set<String> request = new HashSet<>();
      request.add(".Data.Player.Attributes.3"); // Prefix.
      request.add(".Data.Player.Attributes.7"); // Ignored.
      Map<String, Tag> result = NamedTag.quickParse(in, request);
      assertEquals("generic.armor",
          result.get(".Data.Player.Attributes.3").get("Name").stringValue());
      assertEquals("generic.luck",
          result.get(".Data.Player.Attributes.7").get("Name").stringValue());
    }
  }

  /** If one request is a prefix of another request, then only the prefix is in the response. */
  @Test public void testPartial3() throws IOException {
    try (DataInputStream in = FileUtils.openGzipInputStream("testfiles/level.dat")) {
      Set<String> request = new HashSet<>();
      request.add(".Data.Player.Attributes.3"); // Prefix.
      request.add(".Data.Player.Attributes.3.Name"); // Ignored.
      Map<String, Tag> result = NamedTag.quickParse(in, request);
      assertEquals(null, result.get(".Data.Player.Attributes.3.Name").stringValue(null));
      assertEquals("generic.armor",
          result.get(".Data.Player.Attributes.3").get("Name").stringValue());
    }
  }

  @Test public void testPartial4() throws IOException {
    try (DataInputStream in = FileUtils.openGzipInputStream("testfiles/chunk.dat")) {
      Set<String> request = new HashSet<>();
      request.add(".Level.Sections.0.Blocks");
      request.add(".Level.Entities");
      Map<String, Tag> result = NamedTag.quickParse(in, request);
      assertEquals(16 * 16 * 16, result.get(".Level.Sections.0.Blocks").byteArray().length);
      assertEquals(true, result.get(".Level.Entities").isList());
      assertEquals(true, result.get(".Level.Entities").asList().isEmpty());
    }
  }

  /** Test parsing error. */
  @Test public void testEmpty() throws IOException {
    Tag root = FileUtils.read("testfiles/empty.nbt");
    assertTrue(root.isError());
  }

  /** Test parsing error. */
  @Test public void testEmptyList() throws IOException {
    Tag root = FileUtils.read("testfiles/emptylist.nbt");
    assertTrue(root.unpack().get("EmptyList").isList());
    assertTrue(((ListTag) root.unpack().get("EmptyList")).size() == 0);
  }

  @Test public void testError1() throws IOException {
    Tag root = FileUtils.read("testfiles/error1.nbt");
    assertFalse(root.isError());
    assertEquals("Unknown tag type: 104", root.unpack().error());
  }

  /** Malformed list tag that has TAG_End as element type. */
  @Test public void testError2() throws IOException {
    Tag root = FileUtils.read("testfiles/badlist1.nbt");
    assertFalse(root.isError());
    assertEquals("Cannot create list of TAG_End", root.unpack().get("BadList").error());
  }

  /** Malformed list tag: EOF before size. */
  @Test public void testError3() throws IOException {
    Tag root = FileUtils.read("testfiles/badlist2.nbt");
    assertFalse(root.isError());
    assertEquals("IOException while reading TAG_List:\nnull", root.unpack().get("BadList").error());
  }

  /** Malformed compound tag: EOF before item type. */
  @Test public void testError4() throws IOException {
    Tag root = FileUtils.read("testfiles/badcompound1.nbt");
    assertFalse(root.isError());
    // There is a parsing error inside the compound tag. Parsing is aborted and the result is an
    // empty compound tag.
    assertEquals(0, root.asCompound().get("BadCompound").asCompound().size());
  }

  /** Malformed compound tag: EOF before item type. */
  @Test public void testError5() throws IOException {
    Tag root = FileUtils.read("testfiles/badarray1.nbt");
    assertFalse(root.isError());
    assertEquals(true, root.asCompound().get("BadByteArray").isError());
  }

  /** Malformed compound tag: EOF before item type. */
  @Test public void testError6() throws IOException {
    Tag root = FileUtils.read("testfiles/badarray2.nbt");
    assertFalse(root.isError());
    assertEquals(true, root.asCompound().get("BadIntArray").isError());
  }

  /** Test that writing an NBT tree to disk preserves all data. */
  @Test public void testRoundTrip1() throws IOException {
    Tag root = FileUtils.readGzipped("testfiles/level.dat");
    try (FileOutputStream fout = new FileOutputStream("testfiles/level.dat.out");
        GZIPOutputStream gzout = new GZIPOutputStream(fout);
        DataOutputStream out = new DataOutputStream(gzout)) {
      root.write(out);
    }
    Tag root2 = FileUtils.readGzipped("testfiles/level.dat.out");
    assertEquals(root.dumpTree(), root2.dumpTree());
  }

  @Test public void testRoundTrip2() throws IOException {
    Tag root = FileUtils.readGzipped("testfiles/chunk.dat");
    try (FileOutputStream fout = new FileOutputStream("testfiles/chunk.dat.out");
        GZIPOutputStream gzout = new GZIPOutputStream(fout);
        DataOutputStream out = new DataOutputStream(gzout)) {
      root.write(out);
    }
    Tag root2 = FileUtils.readGzipped("testfiles/chunk.dat.out");
    assertEquals(root.dumpTree(), root2.dumpTree());
  }

  @Test public void testRoundTrip3() throws IOException {
    Tag root = new NamedTag("", new CompoundTag(Arrays.asList(
        new NamedTag("bork", new IntArrayTag(new int[] { 38, 39, 20 })),
        new NamedTag("a long array with negative values",
            new LongArrayTag(new long[] { -1, 38, 39, 20, -1001001 })),
        new NamedTag("burk", new LongTag(12)),
        new NamedTag("borks", new ListTag(Tag.TAG_STRING,
            Arrays.asList(new StringTag("foo"), new StringTag("bar"))))
    )));
    try (FileOutputStream fout = new FileOutputStream("testfiles/roundtrip3.dat.out");
        GZIPOutputStream gzout = new GZIPOutputStream(fout);
        DataOutputStream out = new DataOutputStream(gzout)) {
      root.write(out);
    }
    Tag root2 = FileUtils.readGzipped("testfiles/roundtrip3.dat.out");
    assertEquals(root.dumpTree(), root2.dumpTree());
  }
}
