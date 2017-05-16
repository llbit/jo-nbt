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

public class ErrorTag extends SpecificTag {
  public final String message;

  @Override public void write(DataOutputStream out) {
    throw new RuntimeException("Cannot write an error tag to NBT stream (" + getError() + ")");
  }

  public ErrorTag(String message) {
    this.message = message;
  }

  /**
   * Retrieves the error message.
   */
  public String getError() {
    return message != null ? message : "";
  }

  @Override public String extraInfo() {
    return ": \"" + getError() + '"';
  }

  @Override public String type() {
    return "Tag.Error";
  }

  @Override public int tagType() {
    throw new Error("Cannot write an error tag to NBT stream (" + getError() + ")");
  }

  @Override public String name() {
    return "Tag.Error";
  }

  @Override public boolean isEnd() {
    return true;
  }

  @Override public boolean isError() {
    return true;
  }

  @Override public String error() {
    return getError();
  }
}
