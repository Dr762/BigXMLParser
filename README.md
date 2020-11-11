## BigXMLParser

Small app showing how to parse big xml files using SAX.

As input expected two xml files of the same structure, but with different data(old and new)
The files are parsed, their contents are compared and list of ids is returned with marks: m,n,r,p.
Where: 
- m - modified 
- n - new
- p - problem with picture
- r - removed

Demo xml could be found here previously: http://www.mebelforte.ru/yml.xml 

The max file size is 4GB

### Build and run

```yaml
mvn clean install

java -jar <jar-location>/parser.jar
```
