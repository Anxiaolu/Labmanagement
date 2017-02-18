if (!com)
    var com = {}
if (!com.corejsf) {
    var focusLosTimeout
    com.corejsf = {
        errorHandler: function (data) {
            alert("Error occurred during Ajax call:" + data.description)
        },

        updateCompletionItems: function (input, event) {
            var keystorkeTimeout

            jsf.ajax.addOnError(com.corejsf.errorHandler)

            var ajaxRequest = function () {
                jsf.ajax.request(input, event, {
                    render: com.corejsf.getListboxId(input),
                    x: Element.cumulativeOffset(input)[0],
                    y: Element.cumulativeOffset(input)[1] + Element.getHeight(input)
                })
            }

            window.clearTimeout(keystorkeTimeout)
            keystorkeTimeout = window.setTimeout(ajaxRequest, 350)
        },

        inputLostFocus: function (input) {
            var hideListBox = function () {
                Element.hide(com.corejsf.getListboxId(input))
            }

            focusLosTimeout = window.setTimeout(hideListBox, 200)
        },

        getListboxId: function (input) {
            var clientId = new String(input.name)
            var lastIndex = clientId.lastIndexOf(":")
            return clientId.substring(0, lastIndex) + ":listbox"
        }
    }
}