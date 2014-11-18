[![Build Status](https://travis-ci.org/mxro/file-api.svg?branch=master)](https://travis-ci.org/mxro/file-api)


file-api
========

An abstract, cross-platform API for working with files and folders.

### Motivation

This is a very lightweight abstraction on top of vanilla java.io.File. Adding this project as a dependency to your projects should come with very little risk.

Using the FileItem class is infinitely more convenient than using the standard Java classes for many use cases.

### Usages

    FileItem file = FilesJre.wrap(new File("/tmp"))
    
    file.assertFolder("a Folder").createFile("my file.txt").setText("The content")