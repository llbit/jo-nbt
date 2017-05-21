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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Contains multiple tags of the same type.
 */
public class ListTag extends SpecificTag implements Iterable<SpecificTag> {
  public final int type;
  public final List<SpecificTag> items;

  public static SpecificTag read(DataInputStream in) {
    try {
      byte itemType = in.readByte();
      int numItems = in.readInt();
      if (itemType == Tag.TAG_END && numItems > 0) {
        return new ErrorTag("Cannot create list of TAG_End");
      }
      ListTag tagThis = new ListTag(itemType, Collections.<SpecificTag>emptyList());
      for (int i = 0; i < numItems; ++i) {
        SpecificTag last = SpecificTag.read(itemType, in);
        tagThis.add(last);
      }
      return tagThis;
    } catch (IOException e) {
      return new ErrorTag("IOException while reading TAG_List:\n" + e.getMessage());
    }
  }

  @Override public void write(DataOutputStream out) throws IOException {
    out.writeByte(getType());
    out.writeInt(size());
    for (SpecificTag item : items) {
      item.write(out);
    }
  }

  static Map<String, Tag> partialParse(DataInputStream in, String prefix,
      Map<String, Tag> result, Set<String> request, Set<String> prefixes) {

    try {
      byte itemType = in.readByte();
      int numItems = in.readInt();
      if (itemType == Tag.TAG_END && numItems > 0) {
        return result; // Cannot create list of TAG_End.
      }
      for (int i = 0; i < numItems; ++i) {
        String tag = prefix + "." + i;
        boolean parsed = NamedTag.partiallyParseTag(in, result, request, prefixes, itemType, tag);
        if (parsed) {
          if (request.isEmpty()) {
            return result;
          }
        } else {
          SpecificTag.skip(itemType, in);
        }
      }
    } catch (IOException e) {
    }
    return result;
  }

  static void skip(DataInputStream in) {
    try {
      byte itemType = in.readByte();
      int numItems = in.readInt();
      if (itemType == 0) {
        return;
      }
      for (int i = 0; i < numItems; ++i) {
        SpecificTag.skip(itemType, in);
      }
    } catch (IOException e) {
    }
  }

  /**
   * @param type the type of tag that is stored in this list
   * @param items the items of this list
   */
  public ListTag(int type, List<SpecificTag> items) {
    this.type = type;
    this.items = new ArrayList<>(items);
  }

  /**
   * The type of tags stored in this list.
   */
  public int getType() {
    return type;
  }

  /**
   * The number of items in this list.
   */
  public int size() {
    return items.size();
  }

  /**
   * Append an item to this list.
   */
  public void add(SpecificTag node) {
    // TODO: prevent storing the wrong type.
    items.add(node);
  }

  /**
   * Replaces the item at index {@code i} with the new node {@code node}.
   */
  @Override public void set(int i, SpecificTag node) {
    items.set(i, node);
  }

  public String toString() {
    return dumpTree();
  }

  @Override public String type() {
    return "TAG_List";
  }

  @Override public void dumpTree(StringBuilder buff, String indent) {
    buff.append(indent);
    dumpTree(buff);
    for (Tag item : items) {
      item.dumpTree(buff, indent + "  ");
    }
  }

  @Override public String name() {
    return "TAG_List";
  }

  @Override public int tagType() {
    return Tag.TAG_LIST;
  }

  @Override public boolean isList() {
    return true;
  }

  /**
   * Retrieves the item at index {@code i}.
   */
  @Override public Tag get(int i) {
    return items.get(i);
  }

  @Override public Iterator<SpecificTag> iterator() {
    return items.iterator();
  }

  @Override public ListTag asList() {
    return this;
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }
}
