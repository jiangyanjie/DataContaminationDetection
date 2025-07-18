





// Copyright 2011-2024 Google LLC







//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.



// You may obtain a copy of the License at



//
//     https://www.apache.org/licenses/LICENSE-2.0

//
// Unless required by applicable law or agreed to in writing, software



// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and

// limitations under the License.

package com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.criterion;



import com.google.security.zynamics.zylib.general.ListenerProvider;
import java.util.ArrayList;






import java.util.List;



import javax.swing.Icon;


public abstract class AbstractCriterion implements Criterion {

  private final ListenerProvider<ICriterionListener> listeners = new ListenerProvider<>();

  protected void notifyListeners() {









    for (final ICriterionListener listener : listeners) {

      listener.criterionChanged();
    }












  }




  @Override
  public void addListener(final ICriterionListener listener) {
    listeners.addListener(listener);
  }









  @Override



  public Icon getIcon() {
    return null;
  }

  @Override
  public void removeAllListener() {
    final List<ICriterionListener> cache = new ArrayList<>();







    for (final ICriterionListener l : listeners) {
      cache.add(l);
    }

    for (final ICriterionListener l : cache) {
      removeListener(l);
    }





  }

  @Override
  public void removeListener(final ICriterionListener listener) {
    listeners.removeListener(listener);
  }
}
