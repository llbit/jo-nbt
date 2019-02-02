/* Copyright (c) 2010-2017, Jesper Öqvist
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

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;

/**
 * Abstract base class for all kinds of NBT tags.
 *
 * <p>NBT is a tree structure. Compound and List tags are internal
 * nodes in the tree, while other tag types are leafs.
 */
public abstract class Tag {
  // TAG IDs:
  public static final int TAG_END = 0;
  public static final int TAG_BYTE = 1;
  public static final int TAG_SHORT = 2;
  public static final int TAG_INT = 3;
  public static final int TAG_LONG = 4;
  public static final int TAG_FLOAT = 5;
  public static final int TAG_DOUBLE = 6;
  public static final int TAG_BYTE_ARRAY = 7;
  public static final int TAG_STRING = 8;
  public static final int TAG_LIST = 9;
  public static final int TAG_COMPOUND = 10;
  public static final int TAG_INT_ARRAY = 11;
  public static final int TAG_LONG_ARRAY = 12; // Added in Minecraft 1.13.

  private static final ListTag EMPTY_LIST =
      new ListTag(Tag.TAG_END, Collections.<SpecificTag>emptyList());
  private static final CompoundTag EMPTY_COMPOUND = new CompoundTag();

  protected Tag() {
  }

  public static final SpecificTag END = new SpecificTag() {
    @Override public void write(DataOutputStream out) throws IOException {
      writeType(out);
    }

    @Override public String tagName() {
      return "TAG_End";
    }

    @Override public int tagType() {
      return Tag.TAG_END;
    }

    @Override public boolean isEnd() {
      return true;
    }
  };

  public abstract String tagName();

  public boolean isError() {
    return false;
  }

  /**
   * Test if this is a named tag with the given name.
   * @return {@code true} if this is a named tag with the given name.
   */
  public boolean isNamed(String name) {
    return false;
  }

  /**
   * Test if this is a string tag that contains the given string.
   */
  public boolean same(String name) {
    return false;
  }

  /**
   * Builds a string representation of this NBT tag and its children.
   */
  public String dumpTree() {
    StringBuilder buff = new StringBuilder(4096);
    printTag(buff, "");
    return buff.toString();
  }

  /**
   * Print a string representation of this tag to the argument buffer.
   */
  public void printTagInfo(StringBuilder buff) {
    buff.append(tagName()).append(extraInfo()).append('\n');
  }

  /**
   * Print a string representation of this tag to the argument buffer.
   */
  public void printTag(StringBuilder buff, String indent) {
    buff.append(indent);
    printTagInfo(buff);
  }

  /**
   * Extra information for this tag, printed in the tree dump.
   */
  public String extraInfo() {
    return "";
  }

  public abstract void write(DataOutputStream out) throws IOException;

  public String toString() {
    return tagName() + extraInfo();
  }

  public boolean isEnd() {
    return false;
  }

  /**
   * Unwraps a named tag.
   *
   * @return the content of a named tag, or this same tag if it is not a named tag.
   */
  public Tag unpack() {
    return this;
  }

  /**
   * Gives the error message for an error tag.
   */
  public String error() {
    return "";
  }

  public boolean boolValue() {
    return false;
  }

  public boolean boolValue(boolean defaultValue) {
    return defaultValue;
  }

  public int byteValue() {
    return 0;
  }

  public int byteValue(int defaultValue) {
    return defaultValue;
  }

  public short shortValue() {
    return (short) 0;
  }

  public short shortValue(short defaultValue) {
    return defaultValue;
  }

  public int intValue() {
    return 0;
  }

  public int intValue(int defaultValue) {

    return defaultValue;
  }

  public long longValue() {

    return (long) 0;
  }

  public long longValue(long defaultValue) {
    return defaultValue;
  }

  public float floatValue() {
    return (float) 0;
  }

  public float floatValue(float defaultValue) {

    return defaultValue;
  }

  public double doubleValue() {
    return (double) 0;
  }

  public double doubleValue(double defaultValue) {
    return defaultValue;
  }

  public String stringValue() {
    return "";
  }

  public String stringValue(String defaultValue) {
    return defaultValue;
  }

  public byte[] byteArray() {
    return new byte[0];
  }

  public byte[] byteArray(byte[] defaultValue) {
    return defaultValue;
  }

  public int[] intArray() {
    return new int[0];
  }

  public int[] intArray(int[] defaultValue) {
    return defaultValue;
  }

  public long[] longArray() {
    return new long[0];
  }

  public long[] longArray(long[] defaultValue) {
    return defaultValue;
  }

  public boolean isCompoundTag() {
    return false;
  }

  public boolean isList() {
    return false;
  }

  /**
   * Test if this is an int array with a size greater or equal to the given size.
   */
  public boolean isByteArray(int size) {
    return false;
  }

  /**
   * Test if this is an int array with a size greater or equal to the given size.
   */
  public boolean isIntArray(int size) {
    return false;
  }

  /**
   * Test if this is a long array with a size greater or equal to the given size.
   */
  public boolean isLongArray(int size) {
    return false;
  }

  public Tag get(String name) {
    return new ErrorTag("Can not index-by-name in a non-CompoundTag");
  }

  public void set(String name, Tag tag) {
    throw new Error("Can not set item in non-compound tag.");
  }

  public void set(int i, SpecificTag tag) {
    throw new Error("Can not set item in non-list tag.");
  }

  public Tag get(int i) {
    return new ErrorTag("Can not index a non-ListTag");
  }

  /**
   * Returns this tag if it is a list tag, otherwise an empty list.
   */
  public ListTag asList() {
    return EMPTY_LIST;
  }

  /**
   * Returns this tag if it is a list tag, otherwise an empty list.
   */
  public CompoundTag asCompound() {
    return EMPTY_COMPOUND;
  }
}
