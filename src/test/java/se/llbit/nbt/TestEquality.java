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

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestEquality {
  @Test public void testByteTag() {
    assertEquals(new ByteTag(101), new ByteTag(101));
  }

  @Test public void testByteTagNeq() {
    assertNotEquals(new ByteTag(100), new ByteTag(101));
  }

  @Test public void testShortTag() {
    assertEquals(new ShortTag((short) 101), new ShortTag((short) 101));
    assertEquals(new ShortTag(Short.MAX_VALUE), new ShortTag(Short.MAX_VALUE));
  }

  @Test public void testShortTagNeq() {
    assertNotEquals(new ShortTag((short) 1), new ShortTag(Short.MAX_VALUE));
  }

  @Test public void testIntTag() {
    assertEquals(new IntTag(-101), new IntTag(-101));
    assertEquals(new IntTag(Integer.MAX_VALUE), new IntTag(Integer.MAX_VALUE));
  }

  @Test public void testIntTagNeq() {
    assertNotEquals(new IntTag(1), new IntTag(Integer.MAX_VALUE));
  }

  @Test public void testLongTag() {
    assertEquals(new LongTag(-101), new LongTag(-101));
    assertEquals(new LongTag(Long.MAX_VALUE), new LongTag(Long.MAX_VALUE));
  }

  @Test public void testLongTagNeq() {
    assertNotEquals(new LongTag(1), new LongTag(Long.MAX_VALUE));
  }

  @Test public void testFloatTag() {
    assertEquals(new FloatTag(1.0f), new FloatTag(1.0f));
    float pi = 355.f / 133.f;
    assertEquals(new FloatTag(pi), new FloatTag(pi));
  }

  @Test public void testFloatTagNeq() {
    assertNotEquals(new FloatTag(1.0f), new FloatTag(-1.0f));
  }

  @Test public void testDoubleTag() {
    assertEquals(new DoubleTag(-1.0), new DoubleTag(-1.0));
    double pi = 355.0 / 133.0;
    assertEquals(new DoubleTag(pi), new DoubleTag(pi));
  }

  @Test public void testDoubleTagNeq() {
    assertNotEquals(new DoubleTag(1.0), new DoubleTag(-1.0));
  }

  @Test public void testStringTag() {
    assertEquals(new StringTag("x y ?"), new StringTag("x y ?"));
  }

  @Test public void testStringTagNeq() {
    assertNotEquals(new StringTag("x y "), new StringTag("x y ?"));
  }

  @Test public void testEndTag() {
    assertEquals(Tag.END, Tag.END);
  }

  @Test public void testByteArrayTag() {
    assertEquals(
        new ByteArrayTag(new byte[] { 1, 2, 3 }),
        new ByteArrayTag(new byte[] { 1, 2, 3 }));
  }

  @Test public void testByteArrayTagNeq() {
    assertNotEquals(
        new ByteArrayTag(new byte[] { 1, 2, 3 }),
        new ByteArrayTag(new byte[] { 1, 2, 4 }));
  }

  @Test public void testIntArrayTag() {
    assertEquals(
        new IntArrayTag(new int[] {1, 2, 3}),
        new IntArrayTag(new int[] {1, 2, 3}));
  }

  @Test public void testIntArrayTagNeq() {
    assertNotEquals(
        new IntArrayTag(new int[] { 1, 2, 3 }),
        new IntArrayTag(new int[] { 1, 2, 4 }));
  }

  @Test public void testLongArrayTag() {
    assertEquals(
        new LongArrayTag(new long[] { 1, 2, 3 }),
        new LongArrayTag(new long[] { 1, 2, 3 }));
  }

  @Test public void testLongArrayTagNeq() {
    assertNotEquals(
        new LongArrayTag(new long[] { 1, 2, 3 }),
        new LongArrayTag(new long[] { 1, 2, 4 }));
  }

  @Test public void testNamedTag() {
    assertEquals(
        new NamedTag("Bort", new StringTag("foo")),
        new NamedTag("Bort", new StringTag("foo")));
  }

  @Test public void testNamedTagNeq() {
    assertNotEquals(
        new NamedTag("Bort", new StringTag("foo")),
        new NamedTag("Burt", new StringTag("foo")));
  }

  @Test public void testNamedTagNeq2() {
    assertNotEquals(
        new NamedTag("Bort", new StringTag("foo")),
        new NamedTag("Bort", new StringTag("fuu")));
  }

  @Test public void testCompoundTag() {
    CompoundTag t1 = new CompoundTag();
    CompoundTag t2 = new CompoundTag();
    t1.add("Name", new StringTag("Bart Bort"));
    t1.add("Age", new IntTag(13));
    t2.add("Name", new StringTag("Bart Bort"));
    t2.add("Age", new IntTag(13));
    assertEquals(t1, t2);
  }

  @Test public void testCompoundTag2() {
    // Test element permutation.
    CompoundTag t1 = new CompoundTag();
    CompoundTag t2 = new CompoundTag();
    t1.add("Profession", new StringTag("Boss"));
    t1.add("Name", new StringTag("Bart Bort"));
    t1.add("Age", new IntTag(13));
    t1.add("Level", new IntTag(100));
    t2.add("Level", new IntTag(100));
    t2.add("Profession", new StringTag("Boss"));
    t2.add("Age", new IntTag(13));
    t2.add("Name", new StringTag("Bart Bort"));
    assertEquals(t1, t2);
  }

  @Test public void testCompoundTagNeq() {
    CompoundTag t1 = new CompoundTag();
    CompoundTag t2 = new CompoundTag();
    t1.add("Name", new StringTag("Bart Bort"));
    t2.add("Name", new StringTag("Burt Bort"));
    assertNotEquals(t1, t2);
  }

  @Test public void testListTag() {
    ListTag t1 = new ListTag(Tag.TAG_STRING, new ArrayList<StringTag>());
    ListTag t2 = new ListTag(Tag.TAG_STRING, new ArrayList<StringTag>());
    t1.add(new StringTag("Bart Bort"));
    t1.add(new StringTag("13"));
    t2.add(new StringTag("Bart Bort"));
    t2.add(new StringTag("13"));
    assertEquals(t1, t2);
  }

  @Test public void testListTagNeq() {
    ListTag t1 = new ListTag(Tag.TAG_STRING, new ArrayList<StringTag>());
    ListTag t2 = new ListTag(Tag.TAG_STRING, new ArrayList<StringTag>());
    t1.add(new StringTag("Bart Bort"));
    t2.add(new StringTag("Burt Bort"));
    assertNotEquals(t1, t2);
  }

  @Test public void testListTagNeq2() {
    // Test permutation.
    ListTag t1 = new ListTag(Tag.TAG_INT, new ArrayList<IntTag>());
    ListTag t2 = new ListTag(Tag.TAG_INT, new ArrayList<IntTag>());
    t1.add(new IntTag(1));
    t1.add(new IntTag(2));
    t1.add(new IntTag(3));
    t2.add(new IntTag(1));
    t2.add(new IntTag(3));
    t2.add(new IntTag(2));
    assertNotEquals(t1, t2);
  }

  @Test public void testErrorTag() {
    // Error tags never compare equal to anything.
    assertNotEquals(new ErrorTag("error"), new ErrorTag("error"));
  }

  @Test public void testFile() throws IOException {
    Tag f1 = FileUtils.readGzipped("testfiles/level.dat");
    Tag f2 = FileUtils.readGzipped("testfiles/level.dat");
    assertEquals(f1, f2);
  }
}
