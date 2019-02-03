/* Copyright (c) 2018, Jesper Öqvist
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * TAG_Long_Array is an array of long integers (8 bytes).
 *
 * <p>TAG_Long_Array was added in Minecraft 1.13 snapshots.
 */
public class LongArrayTag extends SpecificTag {
  public final long[] value;

  public static SpecificTag read(DataInputStream in) {
    try {
      int length = in.readInt();
      long[] data = new long[length];
      for (int i = 0; i < length; ++i) {
        data[i] = in.readLong();
      }
      return new LongArrayTag(data);
    } catch (IOException e) {
      return new ErrorTag("IOException while reading TAG_Long_Array:\n" + e.getMessage());
    }
  }

  @Override public void write(DataOutputStream out) throws IOException {
    out.writeInt(value.length);
    for (int i = 0; i < value.length; ++i) {
      out.writeLong(value[i]);
    }
  }

  static void skip(DataInputStream in) {
    try {
      int length = in.readInt();
      in.skipBytes(length * 8);
    } catch (IOException e) {
    }
  }

  public LongArrayTag(long[] data) {
    this.value = data;
  }

  public long[] getData() {
    return value;
  }

  @Override public String extraInfo() {
    return ": " + value.length;
  }
  @Override public String tagName() {
    return "TAG_Long_Array";
  }

  public String type() {
    return "TAG_Long_Array";
  }

  @Override public int tagType() {
    return Tag.TAG_LONG_ARRAY;
  }

  @Override public long[] longArray() {
    return value;
  }

  @Override public long[] longArray(long[] defaultValue) {
    return value;
  }

  @Override public boolean isLongArray(int size) {
    return value.length >= size;
  }

  @Override public boolean equals(Object obj) {
    return this == obj
        || (obj instanceof LongArrayTag && Arrays.equals(((LongArrayTag) obj).value, value));
  }
}
