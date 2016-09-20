/**
 * Copyright (c) 2016 Wojciech Tekiela
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.github.wtekiela.opensub4j.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * Hash code is based on Media Player Classic. In natural language it calculates: size + 64bit checksum of the first and
 * last 64k (even if they overlap for files smaller than 128k)
 */
public class OpenSubtitlesFileHashCalculator implements FileHashCalculator {

    private static final int HASH_CHUNK_SIZE = 64 * 1024;

    /**
     * Computes opensubtitles.org compatible hash from file
     *
     * @param file File from which hash is to be computed
     *
     * @return Hash of the file
     *
     * @throws java.io.FileNotFoundException If the named file does not exist, is a directory rather than a regular
     *                                       file, or for some other reason cannot be opened for reading
     * @throws java.io.IOException           If some other I/O error occurs
     */
    public String calculateHash(File file) throws IOException {
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            long size = file.length();

            long chunkSize = Math.min(HASH_CHUNK_SIZE, size);
            long startOffset = 0;
            long endOffset = Math.max(size - HASH_CHUNK_SIZE, 0);

            long head = computeHashForChunk(fileChannel.map(MapMode.READ_ONLY, startOffset, chunkSize));
            long tail = computeHashForChunk(fileChannel.map(MapMode.READ_ONLY, endOffset, chunkSize));

            return String.format("%016x", size + head + tail);
        }
    }

    private long computeHashForChunk(ByteBuffer buffer) {

        LongBuffer longBuffer = buffer.order(ByteOrder.LITTLE_ENDIAN).asLongBuffer();

        long hash = 0;
        while (longBuffer.hasRemaining()) {
            hash += longBuffer.get();
        }
        return hash;
    }
}