package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreModel;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.model.simpleView.SimpleStoreModelView;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreModelView extends SimpleStoreModelView {

    private SimpleProjectView project;

    public StoreModelView() {
        super();
    }

    public StoreModelView(StoreModel storeModel) {
        super(storeModel);

        if (storeModel.getProject() != null) {
            this.project = new SimpleProjectView(storeModel.getProject());
        }
    }

    public SimpleProjectView getProject() {
        return project;
    }

    public void setProject(SimpleProjectView project) {
        this.project = project;
    }
}
