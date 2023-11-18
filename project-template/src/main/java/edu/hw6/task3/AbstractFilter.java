package edu.hw6.task3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    @Override
    boolean accept(Path entry) throws IOException;
    default AbstractFilter and(AbstractFilter other) {
        return new AbstractFilter() {
            @Override
            public boolean accept(Path entry) throws IOException {

                return AbstractFilter.this.accept(entry) && other.accept(entry);
            }
        };
    }

    static AbstractFilter regularFile() {

        return Files::isRegularFile;
    }

    static AbstractFilter readable() {

        return Files::isReadable;
    }

    static AbstractFilter writable() {

        return Files::isWritable;
    }

    static AbstractFilter largerThan(long size) throws IOException {

        return entry -> Files.size(entry) > size;
    }

    static AbstractFilter globMatches(String extension) {
        if(extension.length() < 3) {

           throw new IllegalArgumentException("Invalid extension") ;
        }

        return entry -> entry.toString().endsWith(extension.substring(1));
    }

    static AbstractFilter regexContains(String regex) {
        Pattern pattern = Pattern.compile(".*" + regex + ".*" );

        return entry -> pattern.matcher(entry.getFileName().toString()).matches();
    }

    static AbstractFilter magicNumber(int... magicBytes) throws IOException {
        return entry -> {
            try (ReadableByteChannel channel = Files.newByteChannel(entry)) {
                ByteBuffer buffer = ByteBuffer.allocate(magicBytes.length);
                channel.read(buffer);
                byte[] bytes = buffer.array();
                for (int i = 0; i < magicBytes.length; i++) {
                    if (bytes[i] != magicBytes[i]) {

                        return false;
                    }
                }

                return true;
            }
        };
    }
}
