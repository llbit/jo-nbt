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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Specific tags are all types of tags that can appear explicitly in an NBT file.
 *
 * <p>The only non-sepcific tag is the named tag, which is an implicit construct.
 */
public abstract class SpecificTag extends Tag {
  public static SpecificTag read(byte type, DataInputStream in) {
    switch (type) {
      case Tag.TAG_BYTE:
        return ByteTag.read(in);
      case Tag.TAG_SHORT:
        return ShortTag.read(in);
      case Tag.TAG_INT:
        return IntTag.read(in);
      case Tag.TAG_LONG:
        return LongTag.read(in);
      case Tag.TAG_FLOAT:
        return FloatTag.read(in);
      case Tag.TAG_DOUBLE:
        return DoubleTag.read(in);
      case Tag.TAG_BYTE_ARRAY:
        return ByteArrayTag.read(in);
      case Tag.TAG_STRING:
        return StringTag.read(in);
      case Tag.TAG_LIST:
        return ListTag.read(in);
      case Tag.TAG_COMPOUND:
        return CompoundTag.read(in);
      case Tag.TAG_INT_ARRAY:
        return IntArrayTag.read(in);
      default:
        return new ErrorTag("Unknown tag type: " + type);
    }
  }

  public void writeType(DataOutputStream out) throws IOException {
    out.writeByte(tagType());
  }

  /**
   * Skip the specific tag type in the input stream.
   */
  static void skip(byte type, DataInputStream in) {
    try {
      switch (type) {
        case 1:
          // TAG_Byte
          in.skipBytes(1);
          break;
        case 2:
          // TAG_Short
          in.skipBytes(2);
          break;
        case 3:
          // TAG_Int
          in.skipBytes(4);
          break;
        case 4:
          // TAG_Long
          in.skipBytes(8);
          break;
        case 5:
          // TAG_Float
          in.skipBytes(4);
          break;
        case 6:
          // TAG_Double
          in.skipBytes(8);
          break;
        case 7:
          ByteArrayTag.skip(in);
          break;
        case 8:
          StringTag.skip(in);
          break;
        case 9:
          ListTag.skip(in);
          break;
        case 10:
          CompoundTag.skip(in);
          break;
        case 11:
          IntArrayTag.skip(in);
          break;
      }
    } catch (IOException e) {
    }
  }

  public SpecificTag() {
    super();
  }

  @Override public SpecificTag clone() throws CloneNotSupportedException {
    SpecificTag node = (SpecificTag) super.clone();
    return node;
  }

  public abstract int tagType();
}
