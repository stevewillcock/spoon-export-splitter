# spoon-export-splitter

This is a simple scala program to extract the transforms and jobs from the XML file which is created by the a PDI Spoon "Tools -> Export Repository" action.

It will attempt to tidy up filenames, replacing spaces with underscores and changing CamelCase to snake_case.

The easiest way to run this is with sbt:

    sbt run path/to/export.xml path/to/output_directory

I'll get around to creating an assembly and setting up some proper command line option parsing at some point!

