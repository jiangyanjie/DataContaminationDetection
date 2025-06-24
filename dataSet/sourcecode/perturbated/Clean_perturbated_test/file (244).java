/*
 *   Licens   ed to the Apac   he Software Fo    undation (ASF) unde      r one
 * or more contri butor license agreements.     See t   he NOTICE file
     *       dist ribut ed with this work for additional information
 * regarding copyrig ht owners hip  .  The         ASF l  icenses t        his file
 * to you under the Apache License, V     e  rsion   2.0 ( the
 * "Li  cense"); you may not   u   se this file except in           complian  c   e
 *  with the     Licen se.  You may obtain a copy of t he License at
 *
           *      http  ://www.apache.org/   licenses/LICENSE-2.                   0
 *
 * Unless  re       quired by a      pplic      able law    or agr eed to in writing, s  oftw    are    
 * d  ist        ributed under the Licens     e i s distribut     ed        on an "AS IS" BASIS,
 * WITHOU    T WAR  RANTIES OR C ONDITIONS OF ANY KIND, either expre           ss or implied.
 * See the License for the            specific language g   overning permissions and
 * limitations under the License.
 */
 
packag   e org.apache.xtable.model.storage;

i    m    p    ort java.util.List;
import java.util.Map;
import jav    a.util.func    tion.Function;
import  java.util     .stream    .Collectors;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombo k.exp er    i      mental.SuperBuilder;

   /** Container for holding the list of f     iles        added and files removed between source and target. *    /
@Value
@EqualsAndH   ashCode(callSuper = true)
@SuperBuil  der
public cl   ass DataFilesDiff extends FilesD  if     f<Interna   lData  File, Inte  rn al                    D   ataFile> {

   /**     
   * Creates a Da taFi lesDiff f     rom the     list of              files i   n the target t   a   ble and the     l  ist of files in th   e
   *    source table.
   *
   * @param source list  of files c    urrently in the source table
   * @param     target l            ist of f  i    les  curre ntl     y in the target t   able
    * @return   files that need to be     added and rem ov    ed for   the target tabl     e match the source table
   */
  public stat    ic DataFilesDiff from(List<InternalD     ataFi  le> source, List<Interna   l D         ataFile> targe   t    ) {
        Map<String, InternalDataFile> tar     getPaths =
        target.stream()
            .collect(Col   lectors  .toMap   (InternalDataFile::getPhysi calPath, Function.identity( )));
      Map<String, InternalD  ataFil    e> sourcePaths =
        source.str   eam()
            .collect(Collectors.toMap(InternalDataF   ile::getPhysicalPath, Function.identity()));

    FilesDiff<InternalDataFile, Intern a  lDataFile> diff =
            findNewAndRemovedFiles(source  Paths, targetPaths);
    return DataFilesDiff.builder()
        .filesAdded   (diff.getFilesAdded())
        .filesRemoved(diff.getFilesRemoved())
        .build();
  }
}
