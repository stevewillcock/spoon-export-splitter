# spoon-export-splitter

This is a simple scala program to extract the transforms and jobs from the XML file which is created by the Pentaho Data Integration "Tools -> Export Repository" action. Files are extracted into the same folder structure that was present in the repository.

This will currently attempt to tidy up filenames, replacing spaces with underscores and changing CamelCase to snake_case (there's no option to disable this at the moment - I'll add one later).

The easiest way to run this is with sbt:

    sbt run path/to/export.xml path/to/output_directory

I'll get around to creating an assembly and setting up some proper command line option parsing at some point!

This doesn't currently attempt to remap links between jobs or transformations.

