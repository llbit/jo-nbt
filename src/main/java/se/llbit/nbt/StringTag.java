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

public class StringTag extends SpecificTag {
  // TODO: make value non-null.
  public final String value;

  public static SpecificTag read(DataInputStream in) {
    try {
      // NB: This is not exactly following the minecraft.net spec
      // since the string length is there specified to be a two
      // bytes unsigned integer, but readUTF uses readUnsignedShort
      // for the string length.
      return new StringTag(in.readUTF());
    } catch (IOException e) {
      return new ErrorTag("IOException while reading TAG_String:\n" + e.getMessage());
    }
  }

  @Override public void write(DataOutputStream out) throws IOException {
    out.writeUTF(getData());
  }

  static void skip(DataInputStream in) {
    try {
      short length = in.readShort();
      in.skipBytes(length);
    } catch (IOException e) {
    }
  }

  public StringTag(String value) {
    this.value = value;
  }

  public String getData() {
    return value != null ? value : "";
  }

  @Override public String extraInfo() {
    return ": \"" + getData() + '"';
  }

  @Override public String type() {
    return "TAG_String";
  }

  @Override public String name() {
    return "TAG_String";
  }

  @Override public int tagType() {
    return Tag.TAG_STRING;
  }

  @Override public String stringValue() {
    return getData();
  }

  @Override public String stringValue(String defaultValue) {
    return getData();
  }

  @Override public boolean same(String name) {
    return getData().equals(name);
  }
}
