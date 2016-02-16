function () {
    var category;
    if (this.pages >= 250)
        category = 'Big Books';
    else
        category = "Small Books";
    emit(category, {title: this.title});
};
