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

public class LongTag extends SpecificTag {
  public final long value;

  public static SpecificTag read(DataInputStream in) {
    try {
      return new LongTag(in.readLong());
    } catch (IOException e) {
      return new ErrorTag("IOException while reading TAG_Long:\n" + e.getMessage());
    }
  }

  @Override public void write(DataOutputStream out) throws IOException {
    out.writeLong(getData());
  }

  public LongTag(long value) {
    this.value = value;
  }

  public long getData() {
    return value;
  }

  @Override public String extraInfo() {
    return ": " + getData();
  }

  public String type() {
    return "TAG_Long";
  }

  @Override public String tagName() {
    return "TAG_Long";
  }

  @Override public int tagType() {
    return Tag.TAG_LONG;
  }

  @Override public boolean boolValue() {
    return getData() != 0;
  }

  @Override public boolean boolValue(boolean defaultValue) {
    return getData() != 0;
  }

  @Override public long longValue() {
    return getData();
  }

  @Override public long longValue(long defaultValue) {
    return getData();
  }

  @Override public boolean equals(Object obj) {
    return this == obj || (obj instanceof LongTag && ((LongTag) obj).value == value);
  }
}
