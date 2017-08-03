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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TestTagProperties {
  @Test public void testByteTag() {
    ByteTag tag = new ByteTag(101);

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_BYTE, tag.tagType());
    assertEquals("TAG_Byte: 101", tag.toString());
    assertEquals("TAG_Byte", tag.type());
    assertEquals("TAG_Byte", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed(""));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(101, tag.byteValue());
    assertEquals(101, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    // The boolValue() method tests if a ByteTag is nonzero.
    assertEquals(true, tag.boolValue());
    assertEquals(true, tag.boolValue(false));

    assertEquals(false, new ByteTag(0).boolValue());
    assertEquals(false, new ByteTag(0).boolValue(true));
  }

  @Test public void testShortTag() {
    ShortTag tag = new ShortTag((short) 101);

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_SHORT, tag.tagType());
    assertEquals("TAG_Short: 101", tag.toString());
    assertEquals("TAG_Short", tag.type());
    assertEquals("TAG_Short", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed(""));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(101, tag.shortValue());
    assertEquals(101, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    // The boolValue() method tests if a ShortTag is nonzero.
    assertEquals(true, tag.boolValue());
    assertEquals(true, tag.boolValue(false));

    assertEquals(false, new ShortTag((short) 0).boolValue());
    assertEquals(false, new ShortTag((short) 0).boolValue(true));
  }

  @Test public void testIntTag() {
    IntTag tag = new IntTag(101);

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_INT, tag.tagType());
    assertEquals("TAG_Int: 101", tag.toString());
    assertEquals("TAG_Int", tag.type());
    assertEquals("TAG_Int", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed("int"));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(101, tag.intValue());
    assertEquals(101, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    // The boolValue() method tests if an IntTag is nonzero.
    assertEquals(true, tag.boolValue());
    assertEquals(true, tag.boolValue(false));

    assertEquals(false, new IntTag(0).boolValue());
    assertEquals(false, new IntTag(0).boolValue(true));

    // IntTag has a constructor that takes a boolean value. The value of the IntTag is set
    // to 1 if the boolean is true, 0 otherwise.
    assertEquals(0, new IntTag(false).intValue());
    assertEquals(false, new IntTag(false).boolValue());
    assertEquals(false, new IntTag(false).boolValue(true));

    assertEquals(1, new IntTag(true).intValue());
    assertEquals(true, new IntTag(true).boolValue());
    assertEquals(true, new IntTag(true).boolValue(false));
  }

  @Test public void testLongTag() {
    LongTag tag = new LongTag(101);

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_LONG, tag.tagType());
    assertEquals("TAG_Long: 101", tag.toString());
    assertEquals("TAG_Long", tag.type());
    assertEquals("TAG_Long", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed("bort"));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(101, tag.longValue());
    assertEquals(101, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    // The boolValue() method tests if an LongTag is nonzero.
    assertEquals(true, tag.boolValue());
    assertEquals(true, tag.boolValue(false));

    assertEquals(false, new LongTag(0).boolValue());
    assertEquals(false, new LongTag(0).boolValue(true));
  }

  @Test public void testFloatTag() {
    FloatTag tag = new FloatTag(101);

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_FLOAT, tag.tagType());
    assertEquals("TAG_Float: 101.0", tag.toString());
    assertEquals("TAG_Float", tag.type());
    assertEquals("TAG_Float", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed("bort"));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(101, tag.floatValue(), 0.0001);
    assertEquals(101, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    assertEquals(false, tag.boolValue());
    assertEquals(true, tag.boolValue(true));
  }

  @Test public void testDoubleTag() {
    DoubleTag tag = new DoubleTag(101);

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_DOUBLE, tag.tagType());
    assertEquals("TAG_Double: 101.0", tag.toString());
    assertEquals("TAG_Double", tag.type());
    assertEquals("TAG_Double", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed("burt"));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(101, tag.doubleValue(), 0.0001);
    assertEquals(101, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    assertEquals(false, tag.boolValue());
    assertEquals(true, tag.boolValue(true));
  }

  @Test public void testStringTag() {
    StringTag tag = new StringTag("mystring");

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_STRING, tag.tagType());
    assertEquals("TAG_String: \"mystring\"", tag.toString());
    assertEquals("TAG_String", tag.type());
    assertEquals("TAG_String", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed("burt"));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("mystring", tag.stringValue());
    assertEquals("mystring", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));
    assertTrue(tag.same("mystring"));

    assertEquals(false, tag.boolValue());
    assertEquals(true, tag.boolValue(true));
  }

  @Test public void testEndTag() {
    assertSame(Tag.END, Tag.END.unpack());
    assertEquals(Tag.TAG_END, Tag.END.tagType());
    assertEquals("TAG_End", Tag.END.toString());
    assertEquals("TAG_End", Tag.END.type());
    assertEquals("TAG_End", Tag.END.name());
    assertEquals("", Tag.END.error());
    assertFalse(Tag.END.isError());
    assertTrue(Tag.END.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", Tag.END.get("foo").error());
    assertEquals("Can not index a non-ListTag", Tag.END.get(44).error());

    assertFalse(Tag.END.isList());
    assertFalse(Tag.END.isByteArray(0));
    assertFalse(Tag.END.isByteArray(1));
    assertFalse(Tag.END.isCompoundTag());
    assertFalse(Tag.END.isNamed("burt"));
    assertFalse(Tag.END.isIntArray(0));
    assertFalse(Tag.END.isIntArray(1));
    assertEquals(0, Tag.END.byteArray().length);
    assertEquals(0, Tag.END.intArray().length);
    assertEquals(null, Tag.END.byteArray(null));
    assertEquals(null, Tag.END.intArray(null));

    assertEquals(0, Tag.END.byteValue());
    assertEquals(-1, Tag.END.byteValue(-1));

    assertEquals(0, Tag.END.shortValue());
    assertEquals(-1, Tag.END.shortValue((short) -1));

    assertEquals(0, Tag.END.intValue());
    assertEquals(-1, Tag.END.intValue(-1));

    assertEquals(0, Tag.END.longValue());
    assertEquals(-1, Tag.END.longValue(-1));

    assertEquals(0, Tag.END.floatValue(), 0.0001);
    assertEquals(123, Tag.END.floatValue(123), 0.0001);

    assertEquals(0, Tag.END.doubleValue(), 0.0001);
    assertEquals(123, Tag.END.doubleValue(123), 0.0001);

    assertEquals("", Tag.END.stringValue());
    assertEquals("not string", Tag.END.stringValue("not string"));

    assertEquals(false, Tag.END.boolValue());
    assertEquals(true, Tag.END.boolValue(true));
  }

  @Test public void testByteArrayTag() {
    ByteArrayTag tag = new ByteArrayTag(new byte[] {3, 2, 4, 101});

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_BYTE_ARRAY, tag.tagType());
    assertEquals("TAG_Byte_Array: 4", tag.toString());
    assertEquals("TAG_Byte_Array", tag.type());
    assertEquals("TAG_Byte_Array", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(5));
    assertFalse(tag.isByteArray(100));
    assertTrue(tag.isByteArray(4));
    assertTrue(tag.isByteArray(3));
    assertTrue(tag.isByteArray(0));
    assertTrue(tag.isByteArray(-2));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed("burt"));
    assertFalse(tag.isIntArray(5));
    assertFalse(tag.isIntArray(100));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertFalse(tag.isIntArray(-11));
    assertEquals(4, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertArrayEquals(new byte[] {3,2,4,101}, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    assertEquals(false, tag.boolValue());
    assertEquals(true, tag.boolValue(true));
  }

  @Test public void testIntArrayTag() {
    IntArrayTag tag = new IntArrayTag(new int[] {3, 2, 4, 101});

    assertSame(tag, tag.unpack());
    assertEquals(Tag.TAG_INT_ARRAY, tag.tagType());
    assertEquals("TAG_Int_Array: 4", tag.toString());
    assertEquals("TAG_Int_Array", tag.type());
    assertEquals("TAG_Int_Array", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed("burt"));
    assertFalse(tag.isIntArray(5));
    assertFalse(tag.isIntArray(100));
    assertTrue(tag.isIntArray(4));
    assertTrue(tag.isIntArray(-10));
    assertEquals(0, tag.byteArray().length);
    assertEquals(4, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertArrayEquals(new int[] {3,2,4,101}, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    assertEquals(false, tag.boolValue());
    assertEquals(true, tag.boolValue(true));
  }

  @Test public void testCompoundTag() {
    List<Tag> items = new ArrayList<>();
    items.add(new NamedTag("bart", new IntTag(10)));
    CompoundTag tag = new CompoundTag(items);
    tag.add("burt", new StringTag("bort"));

    assertSame(tag, tag.unpack());
    assertSame(tag, tag.asCompound());
    assertFalse(tag.isEmpty());
    assertEquals(0, tag.asList().size());
    assertEquals(Tag.TAG_COMPOUND, tag.tagType());
    assertEquals("TAG_Compound\n"
        + "  bart:\n"
        + "    TAG_Int: 10\n"
        + "  burt:\n"
        + "    TAG_String: \"bort\"\n", tag.toString());
    assertEquals("TAG_Compound", tag.type());
    assertEquals("TAG_Compound", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("bort", tag.get("burt").stringValue());
    assertEquals("No item named \"foo\" in this compound tag.", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertTrue(tag.isCompoundTag());
    assertFalse(tag.isNamed("burt"));
    assertFalse(tag.isIntArray(-10));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    assertEquals(false, tag.boolValue());
    assertEquals(true, tag.boolValue(true));
  }

  @Test public void testListTag() {
    List<SpecificTag> items = new ArrayList<>();
    items.add(new IntTag(10));
    items.add(new StringTag("bort"));
    ListTag tag = new ListTag(Tag.TAG_STRING, items);

    assertSame(tag, tag.unpack());
    assertSame(tag, tag.asList());
    assertFalse(tag.isEmpty());
    assertEquals(0, tag.asCompound().size());
    assertEquals(Tag.TAG_LIST, tag.tagType());
    assertEquals("TAG_List\n"
        + "  TAG_Int: 10\n"
        + "  TAG_String: \"bort\"\n", tag.toString());
    assertEquals("TAG_List", tag.type());
    assertEquals("TAG_List", tag.name());
    assertEquals("", tag.error());
    assertFalse(tag.isError());
    assertFalse(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("bort", tag.get(1).stringValue());

    assertTrue(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed("burt"));
    assertFalse(tag.isIntArray(-10));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    assertEquals(false, tag.boolValue());
    assertEquals(true, tag.boolValue(true));
  }

  @Test public void testErrorTag() {
    ErrorTag tag = new ErrorTag("failed horribly");

    assertSame(tag, tag.unpack());
    assertEquals("Tag.Error: \"failed horribly\"", tag.toString());
    assertEquals("Tag.Error", tag.type());
    assertEquals("Tag.Error", tag.name());
    assertEquals("failed horribly", tag.error());
    assertTrue(tag.isError());
    assertTrue(tag.isEnd());

    assertEquals("Can not index-by-name in a non-CompoundTag", tag.get("foo").error());
    assertEquals("Can not index a non-ListTag", tag.get(44).error());

    assertFalse(tag.isList());
    assertFalse(tag.isByteArray(0));
    assertFalse(tag.isByteArray(1));
    assertFalse(tag.isCompoundTag());
    assertFalse(tag.isNamed(""));
    assertFalse(tag.isIntArray(0));
    assertFalse(tag.isIntArray(1));
    assertEquals(0, tag.byteArray().length);
    assertEquals(0, tag.intArray().length);
    assertEquals(null, tag.byteArray(null));
    assertEquals(null, tag.intArray(null));

    assertEquals(0, tag.byteValue());
    assertEquals(-1, tag.byteValue(-1));

    assertEquals(0, tag.shortValue());
    assertEquals(-1, tag.shortValue((short) -1));

    assertEquals(0, tag.intValue());
    assertEquals(-1, tag.intValue(-1));

    assertEquals(0, tag.longValue());
    assertEquals(-1, tag.longValue(-1));

    assertEquals(0, tag.floatValue(), 0.0001);
    assertEquals(123, tag.floatValue(123), 0.0001);

    assertEquals(0, tag.doubleValue(), 0.0001);
    assertEquals(123, tag.doubleValue(123), 0.0001);

    assertEquals("", tag.stringValue());
    assertEquals("not string", tag.stringValue("not string"));
    assertFalse(tag.same("not string"));

    assertEquals(false, tag.boolValue());
    assertEquals(true, tag.boolValue(true));
  }

  @Test(expected = Error.class) public void testErrorTag2() {
    ErrorTag tag = new ErrorTag("failed horribly");
    tag.tagType();
  }
}
