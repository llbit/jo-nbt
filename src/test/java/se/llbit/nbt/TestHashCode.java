/* Copyright (c) 2019, Jesper Öqvist
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
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestHashCode {
  @Test public void testByteTag() {
    assertEquals(new ByteTag(101).hashCode(), new ByteTag(101).hashCode());
  }

  @Test public void testByteTagNeq() {
    assertNotEquals(new ByteTag(100).hashCode(), new ByteTag(101).hashCode());
  }

  @Test public void testShortTag() {
    assertEquals(new ShortTag((short) 101).hashCode(), new ShortTag((short) 101).hashCode());
    assertEquals(
        Short.valueOf(Short.MAX_VALUE).hashCode(),
        new ShortTag(Short.MAX_VALUE).hashCode());
  }

  @Test public void testShortTagNeq() {
    assertNotEquals(new ShortTag((short) 1).hashCode(), new ShortTag(Short.MAX_VALUE).hashCode());
  }

  @Test public void testIntTag() {
    assertEquals(new IntTag(-101).hashCode(), new IntTag(-101).hashCode());
    assertEquals(
        Integer.valueOf(Integer.MAX_VALUE).hashCode(),
        new IntTag(Integer.MAX_VALUE).hashCode());
  }

  @Test public void testIntTagNeq() {
    assertNotEquals(new IntTag(1).hashCode(), new IntTag(Integer.MAX_VALUE).hashCode());
  }

  @Test public void testLongTag() {
    assertEquals(new LongTag(-101).hashCode(), new LongTag(-101).hashCode());
    assertEquals(
        new LongTag(Long.MAX_VALUE).hashCode(),
        new LongTag(Long.MAX_VALUE).hashCode());
  }

  @Test public void testLongTagNeq() {
    assertNotEquals(new LongTag(1).hashCode(), new LongTag(Long.MAX_VALUE).hashCode());
  }

  @Test public void testFloatTag() {
    assertEquals(new FloatTag(1.0f).hashCode(), new FloatTag(1.0f).hashCode());
    float pi = 355.f / 133.f;
    assertEquals(new FloatTag(pi).hashCode(), new FloatTag(pi).hashCode());
  }

  @Test public void testFloatTagNeq() {
    assertNotEquals(new FloatTag(1.0f).hashCode(), new FloatTag(-1.0f).hashCode());
  }

  @Test public void testDoubleTag() {
    assertEquals(new DoubleTag(-1.0).hashCode(), new DoubleTag(-1.0).hashCode());
    double pi = 355.0 / 133.0;
    assertEquals(new DoubleTag(pi).hashCode(), new DoubleTag(pi).hashCode());
  }

  @Test public void testDoubleTagNeq() {
    assertNotEquals(new DoubleTag(1.0).hashCode(), new DoubleTag(-1.0).hashCode());
  }

  @Test public void testStringTag() {
    assertEquals(new StringTag("x y ?").hashCode(), new StringTag("x y ?").hashCode());
    assertEquals("x y ?".hashCode(), new StringTag("x y ?").hashCode());
  }

  @Test public void testStringTagNeq() {
    assertNotEquals(new StringTag("x y ").hashCode(), new StringTag("x y ?").hashCode());
  }

  @Test public void testEndTag() {
    assertEquals(Tag.END, Tag.END);
  }

  @Test public void testByteArrayTag() {
    assertEquals(
        new ByteArrayTag(new byte[] { 1, 2, 3 }).hashCode(),
        new ByteArrayTag(new byte[] { 1, 2, 3 }).hashCode());
    assertEquals(
        Arrays.hashCode(new byte[] { 1, 2, 3 }),
        new ByteArrayTag(new byte[] { 1, 2, 3 }).hashCode());
  }

  @Test public void testByteArrayTagNeq() {
    assertNotEquals(
        new ByteArrayTag(new byte[] { 1, 2, 3 }).hashCode(),
        new ByteArrayTag(new byte[] { 1, 2, 4 }).hashCode());
  }

  @Test public void testIntArrayTag() {
    assertEquals(
        new IntArrayTag(new int[] {1, 2, 3}).hashCode(),
        new IntArrayTag(new int[] {1, 2, 3}).hashCode());
    assertEquals(
        Arrays.hashCode(new int[] {1, 2, 3}),
        new IntArrayTag(new int[] {1, 2, 3}).hashCode());
  }

  @Test public void testIntArrayTagNeq() {
    assertNotEquals(
        new IntArrayTag(new int[] { 1, 2, 3 }).hashCode(),
        new IntArrayTag(new int[] { 1, 2, 4 }).hashCode());
    assertNotEquals(
        Arrays.hashCode(new int[] { 1, 2, 3 }),
        new IntArrayTag(new int[] { 1, 2, 4 }).hashCode());
  }

  @Test public void testLongArrayTag() {
    assertEquals(
        new LongArrayTag(new long[] { 1, 2, 3 }).hashCode(),
        new LongArrayTag(new long[] { 1, 2, 3 }).hashCode());
    assertEquals(
        Arrays.hashCode(new long[] { 1, 2, 3 }),
        new LongArrayTag(new long[] { 1, 2, 3 }).hashCode());
  }

  @Test public void testLongArrayTagNeq() {
    assertNotEquals(
        new LongArrayTag(new long[] { 1, 2, 3 }).hashCode(),
        new LongArrayTag(new long[] { 1, 2, 4 }).hashCode());
  }

  @Test public void testNamedTag() {
    assertEquals(
        new NamedTag("Bort", new StringTag("foo")).hashCode(),
        new NamedTag("Bort", new StringTag("foo")).hashCode());
  }

  @Test public void testNamedTagNeq() {
    assertNotEquals(
        new NamedTag("Bort", new StringTag("foo")).hashCode(),
        new NamedTag("Burt", new StringTag("foo")).hashCode());
  }

  @Test public void testNamedTagNeq2() {
    assertNotEquals(
        new NamedTag("Bort", new StringTag("foo")).hashCode(),
        new NamedTag("Bort", new StringTag("fuu")).hashCode());
  }

  @Test public void testCompoundTag() {
    CompoundTag t1 = new CompoundTag();
    CompoundTag t2 = new CompoundTag();
    t1.add("Name", new StringTag("Bart Bort"));
    t1.add("Age", new IntTag(13));
    t2.add("Name", new StringTag("Bart Bort"));
    t2.add("Age", new IntTag(13));
    assertEquals(t1.hashCode(), t2.hashCode());
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
    assertEquals(t1.hashCode(), t2.hashCode());
  }

  @Test public void testCompoundTagNeq() {
    CompoundTag t1 = new CompoundTag();
    CompoundTag t2 = new CompoundTag();
    t1.add("Name", new StringTag("Bart Bort"));
    t2.add("Name", new StringTag("Burt Bort"));
    assertNotEquals(t1.hashCode(), t2.hashCode());
  }

  @Test public void testListTag() {
    ListTag t1 = new ListTag(Tag.TAG_STRING, new ArrayList<StringTag>());
    ListTag t2 = new ListTag(Tag.TAG_STRING, new ArrayList<StringTag>());
    t1.add(new StringTag("Bart Bort"));
    t1.add(new StringTag("13"));
    t2.add(new StringTag("Bart Bort"));
    t2.add(new StringTag("13"));
    assertEquals(t1.hashCode(), t2.hashCode());
  }

  @Test public void testListTagNeq() {
    ListTag t1 = new ListTag(Tag.TAG_STRING, new ArrayList<StringTag>());
    ListTag t2 = new ListTag(Tag.TAG_STRING, new ArrayList<StringTag>());
    t1.add(new StringTag("Bart Bort"));
    t2.add(new StringTag("Burt Bort"));
    assertNotEquals(t1.hashCode(), t2.hashCode());
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
    assertNotEquals(t1.hashCode(), t2.hashCode());
  }

  @Test public void testErrorTag() {
    // Error tags never compare equal to anything.
    assertNotEquals(new ErrorTag("error").hashCode(), new ErrorTag("error").hashCode());
  }

  @Test public void testFile() throws IOException {
    Tag f1 = FileUtils.readGzipped("testfiles/level.dat");
    Tag f2 = FileUtils.readGzipped("testfiles/level.dat");
    assertEquals(f1.hashCode(), f2.hashCode());
  }
}
