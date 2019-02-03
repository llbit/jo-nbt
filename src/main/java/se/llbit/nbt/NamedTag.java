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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Named tags are the items of a compound tag.
 */
public class NamedTag extends Tag {
  public final String name;
  public final SpecificTag tag;

  public NamedTag(String name, SpecificTag tag) {
    this.name = name;
    this.tag = tag;
  }

  public static Tag read(DataInputStream in) {
    try {
      byte type = in.readByte();
      if (type == 0) {
        return Tag.END;
      } else {
        SpecificTag name = StringTag.read(in);
        SpecificTag payload = SpecificTag.read(type, in);
        return new NamedTag(name.stringValue(), payload);
      }
    } catch (IOException e) {
      return new ErrorTag("IOException while reading tag type:\n" + e.getMessage());
    }
  }

  @Override public void write(DataOutputStream out) throws IOException {
    getTag().writeType(out);
    StringTag.write(out, name);
    getTag().write(out);
  }

  /**
   * Parse only the requested tags.
   *
   * @param in The input stream of the NBT data
   * @param request A set containing the requested tags
   * @return Requested tag paths mapped to parsed tag data
   */
  public static Map<String, Tag> quickParse(DataInputStream in, Set<String> request) {
    // Create a tree of tags to be parsed, skip parsing for other tags.
    Map<String, Tag> result = new HashMap<>();
    Set<String> prefixes = new HashSet<>();

    for (String tag : request) {
      String[] parts = tag.split("\\.");
      String prefix = "";
      for (int i = 0; i < parts.length - 1; ++i) {
        if (i > 0) {
          prefix += ".";
        }
        prefix += parts[i];
        prefixes.add(prefix);
      }
    }

    // Initialize result map so it contains error nodes for each requested tag.
    for (String tag : request) {
      result.put(tag, new ErrorTag("[not loaded]"));
    }

    return partialParse(in, result, request, prefixes);
  }

  private static Map<String, Tag> partialParse(DataInputStream in, Map<String, Tag> result,
      Set<String> request, Set<String> prefixes) {
    try {
      byte type = in.readByte();
      if (type != 0) {
        SpecificTag name = StringTag.read(in);
        String tag = name.stringValue(); // We have no prefix as this is the top level tag.
        partiallyParseTag(in, result, request, prefixes, type, tag);
      }
    } catch (IOException e) {
    }
    // No tags were read.
    return result;
  }

  static boolean partiallyParseTag(DataInputStream in,
      Map<String, Tag> result,
      Set<String> request, Set<String> prefixes,
      byte type, String tag) {
    if (request.contains(tag)) {
      // Read this tag fully.
      SpecificTag payload = SpecificTag.read(type, in);
      result.put(tag, payload);
      request.remove(tag);
      return true;
    } else if (prefixes.contains(tag)) {
      // Partially parse this tag.
      if (type == TAG_LIST) {
        ListTag.partialParse(in, tag, result, request, prefixes);
      } else if (type == TAG_COMPOUND) {
        CompoundTag.partialParse(in, tag, result, request, prefixes);
      }
      return true;
    }
    return false;
  }

  /**
   * Gives the name of this named tag.
   *
   * @return The current node used as the Name child.
   */
  public String name() {
    return name;
  }

  /**
   * Retrieves the Tag child.
   *
   * @return The current node used as the Tag child.
   */
  public SpecificTag getTag() {
    return tag;
  }

  @Override public String tagName() {
    return "TAG:named";
  }

  @Override public Tag unpack() {
    return getTag();
  }

  @Override public boolean isNamed(String name) {
    return this.name.equals(name);
  }

  @Override public void printTag(StringBuilder buff, String indent) {
    buff.append(indent);
    printTagInfo(buff);
    buff.append(indent).append("  ").append("TAG_String: \"").append(name).append("\"\n");
    tag.printTag(buff, indent + "  ");
  }

  @Override public ListTag asList() {
    return tag.asList();
  }

  @Override public CompoundTag asCompound() {
    return tag.asCompound();
  }

  @Override public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof NamedTag)) {
      return false;
    }
    NamedTag other = (NamedTag) obj;
    return name.equals(other.name) && tag.equals(other.tag);
  }
}
